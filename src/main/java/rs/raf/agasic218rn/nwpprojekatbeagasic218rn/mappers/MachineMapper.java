package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.mappers;

import org.springframework.stereotype.Component;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.Machine;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.Status;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.User;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.requests.MachineRequest;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.responses.MachineResponse;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.responses.StateChangeMessage;

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
