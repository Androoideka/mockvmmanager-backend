package io.github.androoideka.vm_manager.exceptions;

import io.github.androoideka.vm_manager.model.MachineOperation;
import io.github.androoideka.vm_manager.model.Status;

public class OperationException extends RuntimeException {
    private final MachineOperation machineOperation;
    private final Status status;

    public OperationException(String message, MachineOperation machineOperation, Status status) {
        super(message);
        this.machineOperation = machineOperation;
        this.status = status;
    }

    public MachineOperation getOperation() {
        return machineOperation;
    }

    public Status getStatus() {
        return status;
    }
}
