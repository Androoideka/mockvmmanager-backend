package io.github.androoideka.vm_manager.exceptions;

import java.util.Locale;

import io.github.androoideka.vm_manager.model.MachineOperation;
import io.github.androoideka.vm_manager.model.Status;

public class ConcurrentOperationException extends RuntimeException {

    public ConcurrentOperationException(MachineOperation machineOperation, Status status) {
        super(ConcurrentOperationException.generateMessage(machineOperation, status));
    }

    public static String generateMessage(MachineOperation machineOperation, Status status) {
        return "Machine could not be "
                + MachineOperation.past(machineOperation).toLowerCase(Locale.ROOT)
                + " because another operation was already executing.";
    }
}
