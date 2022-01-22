package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.exceptions;

import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.MachineOperation;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.Status;

import java.util.Locale;

public class ConcurrentOperationException extends RuntimeException {
    private final MachineOperation machineOperation;
    private final Status status;

    public ConcurrentOperationException(MachineOperation machineOperation, Status status) {
        super(ConcurrentOperationException.generateMessage(machineOperation, status));
        this.machineOperation = machineOperation;
        this.status = status;
    }

    public static String generateMessage(MachineOperation machineOperation, Status status) {
        return "Machine could not be "
                + MachineOperation.past(machineOperation).toLowerCase(Locale.ROOT)
                + " because another operation was already executing.";
    }
}
