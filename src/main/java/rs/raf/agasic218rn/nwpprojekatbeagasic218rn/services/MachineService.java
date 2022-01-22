package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.services;

import org.springframework.data.domain.Page;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.MachineOperation;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.requests.MachineRequest;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.responses.MachineResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MachineService {
    Page<MachineResponse> search(String name, List<String> statuses, LocalDate dateFrom, LocalDate dateTo, Integer page, Integer size);
    void executeOperation(Long machineId, MachineOperation machineOperation);
    void scheduleOperation(Long machineId, MachineOperation machineOperation, String cron);
    MachineResponse create(MachineRequest machineRequest);
    void destroy(Long machineId);
}
