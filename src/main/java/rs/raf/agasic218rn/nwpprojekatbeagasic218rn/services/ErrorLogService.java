package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.services;

import org.springframework.data.domain.Page;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.Machine;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.MachineOperation;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.responses.ErrorLogResponse;

public interface ErrorLogService {
    void generateInvalidStateError(MachineOperation machineOperation, Machine machine);
    void generateConcurrencyError(MachineOperation machineOperation, Machine machine);
    Page<ErrorLogResponse> listErrors(Integer page, Integer size);
}
