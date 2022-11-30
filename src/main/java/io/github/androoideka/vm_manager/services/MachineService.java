package io.github.androoideka.vm_manager.services;

import org.springframework.data.domain.Page;

import io.github.androoideka.vm_manager.model.MachineOperation;
import io.github.androoideka.vm_manager.requests.MachineRequest;
import io.github.androoideka.vm_manager.responses.MachineResponse;

import java.time.LocalDate;
import java.util.List;

public interface MachineService {
    Page<MachineResponse> search(String name, List<String> statuses, LocalDate dateFrom, LocalDate dateTo, Integer page,
            Integer size);

    void executeOperation(Long machineId, MachineOperation machineOperation);

    void scheduleOperation(Long machineId, MachineOperation machineOperation, String cron);

    MachineResponse create(MachineRequest machineRequest);

    void destroy(Long machineId);
}
