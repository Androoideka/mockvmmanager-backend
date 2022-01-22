package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.services;

import org.springframework.data.domain.Page;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.exceptions.ErrorCause;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.ErrorLog;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.Machine;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.MachineOperation;

public interface ErrorLogService {
    void generateError(MachineOperation machineOperation, Machine machine, ErrorCause errorCause);
    Page<ErrorLog> listErrors(Long userId);
}
