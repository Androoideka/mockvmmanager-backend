package io.github.androoideka.vm_manager.mappers;

import org.springframework.stereotype.Component;

import io.github.androoideka.vm_manager.model.ErrorLog;
import io.github.androoideka.vm_manager.responses.ErrorLogResponse;

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
