package io.github.androoideka.vm_manager.exceptions;

import java.util.Locale;

import io.github.androoideka.vm_manager.model.MachineOperation;
import io.github.androoideka.vm_manager.model.Status;

public class InvalidMachineStateException extends RuntimeException {

    public InvalidMachineStateException(MachineOperation machineOperation, Status status) {
        super(InvalidMachineStateException.generateMessage(machineOperation, status));
    }

    public static String generateMessage(MachineOperation machineOperation, Status status) {
        return "Could not " + machineOperation.toString().toLowerCase(Locale.ROOT)
                + " machine because the machine was "
                + status.toString().toLowerCase(Locale.ROOT) + '.';
    }
}
