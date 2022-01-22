package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.responses;

import java.time.LocalDateTime;

public class ErrorLogResponse {
    private Long errorId;
    private LocalDateTime dateTime;
    private String machineName;
    private String message;

    public ErrorLogResponse() {
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

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
