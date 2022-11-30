package io.github.androoideka.vm_manager.mappers;

import org.springframework.stereotype.Component;

import io.github.androoideka.vm_manager.model.Machine;
import io.github.androoideka.vm_manager.model.Status;
import io.github.androoideka.vm_manager.model.User;
import io.github.androoideka.vm_manager.requests.MachineRequest;
import io.github.androoideka.vm_manager.responses.MachineResponse;
import io.github.androoideka.vm_manager.responses.StateChangeMessage;

import java.time.LocalDateTime;

@Component
public class MachineMapper {
    public MachineResponse machineToMachineResponse(Machine machine) {
        MachineResponse machineResponse = new MachineResponse();
        machineResponse.setMachineId(machine.getMachineId());
        machineResponse.setName(machine.getName());
        machineResponse.setCreated(machine.getCreated());
        machineResponse.setStatus(machine.getStatus());
        machineResponse.setOperationsLeft(machine.getOpCounter() - machine.getVersion());
        return machineResponse;
    }

    public Machine machineRequestToMachine(MachineRequest machineRequest, User user) {
        Machine machine = new Machine();
        machine.setName(machineRequest.getName());
        machine.setCreated(LocalDateTime.now());
        machine.setStatus(Status.STOPPED);
        machine.setActive(true);
        machine.setCreatedBy(user);
        machine.setVersion(0L);
        machine.setOpCounter(0L);
        return machine;
    }

    public StateChangeMessage machineToMachineMessage(Machine machine) {
        StateChangeMessage stateChangeMessage = new StateChangeMessage();
        stateChangeMessage.setMachineId(machine.getMachineId());
        stateChangeMessage.setStatus(machine.getStatus());
        stateChangeMessage.setOperationsLeft(machine.getOpCounter() - machine.getVersion());
        stateChangeMessage.setUserId(machine.getCreatedBy().getUserId());
        return stateChangeMessage;
    }
}
