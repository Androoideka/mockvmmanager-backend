package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long machineId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne
    private User createdBy;

    @Column(nullable = false)
    private boolean active;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(nullable = false)
    private Long opCounter;

    public Machine() {
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getOpCounter() {
        return opCounter;
    }

    public void setOpCounter(Long opCounter) {
        this.opCounter = opCounter;
    }
}
