package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.exceptions;

import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.MachineOperation;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.Status;

import java.util.Locale;

public class InvalidMachineStateException extends RuntimeException {
    private final MachineOperation machineOperation;
    private final Status status;

    public InvalidMachineStateException(MachineOperation machineOperation, Status status) {
        super(InvalidMachineStateException.generateMessage(machineOperation, status));
        this.machineOperation = machineOperation;
        this.status = status;
    }

    public static String generateMessage(MachineOperation machineOperation, Status status) {
        return "Could not " + machineOperation.toString().toLowerCase(Locale.ROOT)
                + " machine because the machine was "
                + status.toString().toLowerCase(Locale.ROOT);
    }
}
