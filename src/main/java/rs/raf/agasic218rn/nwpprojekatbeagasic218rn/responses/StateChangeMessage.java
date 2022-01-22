package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.responses;

import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.Status;

public class StateChangeMessage {
    private Long machineId;
    private Status status;

    public StateChangeMessage(Long machineId, Status status) {
        this.machineId = machineId;
        this.status = status;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
