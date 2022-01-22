package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.mappers;

import org.springframework.stereotype.Component;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.ErrorLog;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.responses.ErrorLogResponse;

@Component
public class ErrorMapper {
    public ErrorLogResponse errorLogToErrorLogResponse(ErrorLog errorLog) {
        ErrorLogResponse errorLogResponse = new ErrorLogResponse();
        errorLogResponse.setErrorId(errorLog.getErrorId());
        errorLogResponse.setDateTime(errorLog.getDateTime());
        errorLogResponse.setMachineName(errorLog.getMachine().getName());
        errorLogResponse.setMessage(errorLog.getMessage());
        return errorLogResponse;
    }
}
