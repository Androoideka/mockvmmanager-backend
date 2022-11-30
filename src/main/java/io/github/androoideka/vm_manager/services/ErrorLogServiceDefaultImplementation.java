package io.github.androoideka.vm_manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.github.androoideka.vm_manager.exceptions.ConcurrentOperationException;
import io.github.androoideka.vm_manager.exceptions.InvalidMachineStateException;
import io.github.androoideka.vm_manager.mappers.ErrorMapper;
import io.github.androoideka.vm_manager.model.ErrorLog;
import io.github.androoideka.vm_manager.model.Machine;
import io.github.androoideka.vm_manager.model.MachineOperation;
import io.github.androoideka.vm_manager.repositories.ErrorRepository;
import io.github.androoideka.vm_manager.responses.ErrorLogResponse;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ErrorLogServiceDefaultImplementation implements ErrorLogService {
    private final ErrorRepository errorRepository;
    private final ErrorMapper errorMapper;
    private final UserService userService;

    @Autowired
    public ErrorLogServiceDefaultImplementation(ErrorRepository errorRepository, ErrorMapper errorMapper,
            UserService userService) {
        this.errorRepository = errorRepository;
        this.errorMapper = errorMapper;
        this.userService = userService;
    }

    @Override
    public void generateInvalidStateError(MachineOperation machineOperation, Machine machine) {
        ErrorLog errorLog = new ErrorLog(machineOperation, machine,
                InvalidMachineStateException.generateMessage(machineOperation, machine.getStatus()));
        this.errorRepository.save(errorLog);
    }

    @Override
    public void generateConcurrencyError(MachineOperation machineOperation, Machine machine) {
        ErrorLog errorLog = new ErrorLog(machineOperation, machine,
                ConcurrentOperationException.generateMessage(machineOperation, machine.getStatus()));
        this.errorRepository.save(errorLog);
    }

    @Override
    public Page<ErrorLogResponse> listErrors(Integer page, Integer size) {
        Set<Long> machineIds = userService.getCurrentUser().getMachines().stream().map(Machine::getMachineId)
                .collect(Collectors.toSet());
        return this.errorRepository.findAll(new ErrorLogSpecification(machineIds), PageRequest.of(page, size))
                .map(errorMapper::errorLogToErrorLogResponse);
    }
}
