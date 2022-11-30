package io.github.androoideka.vm_manager.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.androoideka.vm_manager.model.Status;

public class StateChangeMessage {
    private Long machineId;
    private Status status;
    private Long operationsLeft;
    @JsonIgnore
    private Long userId;

    public StateChangeMessage() {

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

    public Long getOperationsLeft() {
        return operationsLeft;
    }

    public void setOperationsLeft(Long operationsLeft) {
        this.operationsLeft = operationsLeft;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
