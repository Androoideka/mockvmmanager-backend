package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.exceptions;

import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.MachineOperation;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.Status;

import java.util.Locale;

public enum ErrorCause {
    CONCURRENCY, INVALID_STATE;

    public static String generateMessage(ErrorCause errorCause, MachineOperation machineOperation, Status status) {
        if(errorCause == ErrorCause.CONCURRENCY) {
            return "Machine could not be "
                    + MachineOperation.past(machineOperation).toLowerCase(Locale.ROOT)
                    + " because another operation was already executing.";
        } else if(errorCause == ErrorCause.INVALID_STATE) {
            return "Could not " + machineOperation.toString().toLowerCase(Locale.ROOT)
                    + " machine because the machine was "
                    + status.toString().toLowerCase(Locale.ROOT);
        } else {
            return "Unknown error";
        }
    }
}
