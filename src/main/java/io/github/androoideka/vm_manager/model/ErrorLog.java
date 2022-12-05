package io.github.androoideka.vm_manager.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long errorId;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MachineOperation machineOperation;

    @Column(nullable = false)
    private String message;

    @ManyToOne
    private Machine machine;

    public ErrorLog() {

    }

    public ErrorLog(MachineOperation machineOperation, Machine machine, String message) {
        this.dateTime = LocalDateTime.now();
        this.machineOperation = machineOperation;
        this.machine = machine;
        this.message = message;
    }

    public Long getErrorId() {
        return errorId;
    }

    public void setErrorId(Long errorId) {
        this.errorId = errorId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public MachineOperation getMachineOperation() {
        return machineOperation;
    }

    public void setMachineOperation(MachineOperation machineOperation) {
        this.machineOperation = machineOperation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
