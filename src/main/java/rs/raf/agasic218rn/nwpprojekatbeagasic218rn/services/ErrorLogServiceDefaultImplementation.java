package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.exceptions.ConcurrentOperationException;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.exceptions.InvalidMachineStateException;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.mappers.ErrorMapper;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.ErrorLog;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.Machine;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.MachineOperation;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.repositories.ErrorRepository;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.responses.ErrorLogResponse;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ErrorLogServiceDefaultImplementation implements ErrorLogService {
    private final ErrorRepository errorRepository;
    private final ErrorMapper errorMapper;
    private final UserService userService;

    @Autowired
    public ErrorLogServiceDefaultImplementation(ErrorRepository errorRepository, ErrorMapper errorMapper, UserService userService) {
        this.errorRepository = errorRepository;
        this.errorMapper = errorMapper;
        this.userService = userService;
    }

    @Override
    public void generateInvalidStateError(MachineOperation machineOperation, Machine machine) {
        ErrorLog errorLog = new ErrorLog(machineOperation, machine, InvalidMachineStateException.generateMessage(machineOperation, machine.getStatus()));
        this.errorRepository.save(errorLog);
    }

    @Override
    public void generateConcurrencyError(MachineOperation machineOperation, Machine machine) {
        ErrorLog errorLog = new ErrorLog(machineOperation, machine, ConcurrentOperationException.generateMessage(machineOperation, machine.getStatus()));
        this.errorRepository.save(errorLog);
    }

    @Override
    public Page<ErrorLogResponse> listErrors(Integer page, Integer size) {
        Set<Long> machineIds = userService.getCurrentUser().getMachines().stream().map(Machine::getMachineId).collect(Collectors.toSet());
        return this.errorRepository.findAll(new ErrorLogSpecification(machineIds), PageRequest.of(page, size)).map(errorMapper::errorLogToErrorLogResponse);
    }
}
