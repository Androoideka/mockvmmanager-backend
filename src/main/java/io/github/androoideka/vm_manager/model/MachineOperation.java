package io.github.androoideka.vm_manager.model;

public enum MachineOperation {
    START, STOP, RESTART, DESTROY;

    public static String past(MachineOperation machineOperation) {
        if (machineOperation == MachineOperation.START) {
            return "STARTED";
        } else if (machineOperation == MachineOperation.STOP) {
            return "STOPPED";
        } else if (machineOperation == MachineOperation.RESTART) {
            return "RESTARTED";
        } else if (machineOperation == MachineOperation.DESTROY) {
            return "DESTROYED";
        }
        return "";
    }
}
