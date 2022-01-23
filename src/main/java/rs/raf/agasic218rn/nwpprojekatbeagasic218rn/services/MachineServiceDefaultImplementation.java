package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.controllers.MessageController;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.exceptions.ConcurrentOperationException;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.exceptions.InvalidMachineStateException;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.mappers.MachineMapper;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.*;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.repositories.MachineRepository;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.requests.MachineRequest;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.responses.MachineResponse;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.responses.StateChangeMessage;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MachineServiceDefaultImplementation implements MachineService {
    private static final long TIME_INCREMENT = 5000;
    private final MachineMapper machineMapper;
    private final MachineRepository machineRepository;
    private final UserService userService;
    private final ErrorLogService errorLogService;
    private final TaskScheduler taskScheduler;
    private final MessageController messageController;

    @Autowired
    public MachineServiceDefaultImplementation(MachineMapper machineMapper, MachineRepository machineRepository, UserService userService, ErrorLogService errorLogService, TaskScheduler taskScheduler, MessageController messageController) {
        this.machineMapper = machineMapper;
        this.machineRepository = machineRepository;
        this.userService = userService;
        this.errorLogService = errorLogService;
        this.taskScheduler = taskScheduler;
        this.messageController = messageController;
    }

    @Override
    public MachineResponse create(MachineRequest machineRequest) {
        Machine machine = this.machineMapper.machineRequestToMachine(machineRequest, userService.getCurrentUser());
        machine = this.machineRepository.save(machine);
        return this.machineMapper.machineToMachineResponse(machine);
    }

    @Override
    public Page<MachineResponse> search(String name, List<String> statuses, LocalDate dateFrom, LocalDate dateTo, Integer page, Integer size) {
        MachineSpecification specification = new MachineSpecification(name, statuses, dateFrom, dateTo,
                userService.getCurrentUser().getUserId());
        return this.machineRepository.findAll(specification, PageRequest.of(page, size))
                .map(this.machineMapper::machineToMachineResponse);
    }

    @Override
    public void executeOperation(Long machineId, MachineOperation machineOperation) {
        Machine machine = this.get(machineId);
        // Initiate checks
        if(!this.isValidState(machine, machineOperation)) {
            throw new InvalidMachineStateException(machineOperation, machine.getStatus());
        }
        if(!this.isReadyToExecute(machine, machineOperation)) {
            throw new ConcurrentOperationException(machineOperation, machine.getStatus());
        }
        commenceOperation(machine, machineOperation);
    }

    @Override
    public void scheduleOperation(Long machineId, MachineOperation machineOperation, String cron) {
        this.get(machineId);
        CronTrigger cronTrigger = new CronTrigger(cron);
        this.taskScheduler.schedule(() -> {
            // Check if machine still exists
            Optional<Machine> machineOptional = this.machineRepository
                    .findById(machineId);
            if(machineOptional.isPresent()) {
                Machine machineCurr = machineOptional.get();
                if(this.isValidState(machineCurr, machineOperation)) {
                    if(this.isReadyToExecute(machineCurr, machineOperation)) {
                        commenceOperation(machineCurr, machineOperation);
                    }
                }
            }
        }, cronTrigger);
    }

    private Machine get(Long machineId) {
        Machine machine = this.machineRepository
                .findById(machineId)
                .orElseThrow(() -> new AccessDeniedException("This machine does not exist or does not belong to you."));
        if(!userService.getCurrentUser().getUserId().equals(machine.getCreatedBy().getUserId())) {
            throw new AccessDeniedException("This machine does not exist or does not belong to you.");
        }
        return machine;
    }

    private boolean isValidState(Machine machine, MachineOperation machineOperation) {
        if(machineOperation == MachineOperation.START && machine.getStatus() == Status.STOPPED) {
            return true;
        }
        if(machineOperation == MachineOperation.STOP && machine.getStatus() == Status.RUNNING) {
            return true;
        }
        if(machineOperation == MachineOperation.RESTART && machine.getStatus() == Status.RUNNING) {
            return true;
        }
        this.errorLogService.generateInvalidStateError(machineOperation, machine);
        return false;
    }

    private boolean isReadyToExecute(Machine machine, MachineOperation machineOperation) {
        Integer modified = this.machineRepository.incrementOpCounter(machine.getMachineId());
        if(modified.equals(0)) {
            this.errorLogService.generateConcurrencyError(machineOperation, machine);
            return false;
        }
        // Updating the opCounter on the object manually because getting it from the database doesn't work due to some JPA cache and I don't want to purge it
        machine.setOpCounter(machine.getOpCounter() + 1);
        return true;
    }

    private void commenceOperation(Machine machine, MachineOperation machineOperation) {
        if(machineOperation == MachineOperation.DESTROY) {
            machine.setActive(false);
            this.machineRepository.save(machine);
            return;
        }
        long randomDelay = (long) (Math.random() * TIME_INCREMENT);
        long totalDelay = randomDelay + TIME_INCREMENT;
        if(machineOperation != MachineOperation.RESTART) {
            totalDelay += TIME_INCREMENT;
        }
        this.taskScheduler.schedule(() ->
                finishOperation(machine, machineOperation), new Date(System.currentTimeMillis() + totalDelay));
    }

    private void finishOperation(Machine machine, MachineOperation machineOperation) {
        if(machineOperation == MachineOperation.START) {
            machine.setStatus(Status.RUNNING);
        } else if(machineOperation == MachineOperation.STOP) {
            machine.setStatus(Status.STOPPED);
        } else if(machineOperation == MachineOperation.RESTART) {
            // Has to be modified one more time
            machine.setOpCounter(machine.getOpCounter() + 1);
            machine.setStatus(Status.STOPPED);
        }
        machine = this.machineRepository.save(machine);
        messageController.sendNewState(machineMapper.machineToMachineMessage(machine));
        if(machineOperation == MachineOperation.RESTART) {
            long randomDelay = (long) (Math.random() * TIME_INCREMENT);
            long totalDelay = randomDelay + TIME_INCREMENT;
            Machine finalMachine = machine;
            this.taskScheduler.schedule(() -> {
                finalMachine.setStatus(Status.RUNNING);
                this.machineRepository.save(finalMachine);
                messageController.sendNewState(machineMapper.machineToMachineMessage(finalMachine));
            }, new Date(System.currentTimeMillis() + totalDelay));
        }
    }

    @Override
    public void destroy(Long machineId) {
        Machine machine = this.get(machineId);
        MachineOperation machineOperation = MachineOperation.DESTROY;
        if(machine.getStatus() != Status.STOPPED) {
            throw new InvalidMachineStateException(machineOperation, machine.getStatus());
        }
        if(!this.isReadyToExecute(machine, MachineOperation.DESTROY)) {
            throw new ConcurrentOperationException(machineOperation, machine.getStatus());
        }
        machine.setActive(false);
        this.machineRepository.save(machine);
    }
}
