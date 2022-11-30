package io.github.androoideka.vm_manager.services;

import org.springframework.data.domain.Page;

import io.github.androoideka.vm_manager.model.Machine;
import io.github.androoideka.vm_manager.model.MachineOperation;
import io.github.androoideka.vm_manager.responses.ErrorLogResponse;

public interface ErrorLogService {
    void generateInvalidStateError(MachineOperation machineOperation, Machine machine);

    void generateConcurrencyError(MachineOperation machineOperation, Machine machine);

    Page<ErrorLogResponse> listErrors(Integer page, Integer size);
}
