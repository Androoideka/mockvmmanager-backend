package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.exceptions;

import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.Status;

public class InvalidMachineStateException extends RuntimeException {
    private Status status;

    public InvalidMachineStateException(String message, Status status) {
        super(message);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
