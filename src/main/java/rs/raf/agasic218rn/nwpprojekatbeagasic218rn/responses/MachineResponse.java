package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.responses;

import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.Status;

import java.time.LocalDateTime;

public class MachineResponse {
    private Long machineId;
    private String name;
    private LocalDateTime created;
    private Status status;
    private Long operationsLeft;

    public MachineResponse() {
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getOperationsLeft() {
        return operationsLeft;
    }

    public void setOperationsLeft(Long operationsLeft) {
        this.operationsLeft = operationsLeft;
    }
}
