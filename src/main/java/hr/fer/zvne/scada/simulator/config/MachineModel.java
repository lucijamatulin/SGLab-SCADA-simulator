package hr.fer.zvne.scada.simulator.config;

import java.util.Map;

public class MachineModel {
    private int id;
    private Map<String, String> data;
    private Map<String, MachineProperties> measurements;
    private Map<String, CommandProperties> commands;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public Map<String, MachineProperties> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Map<String, MachineProperties> measurements) {
        this.measurements = measurements;
    }

    public Map<String, CommandProperties> getCommands() {
        return commands;
    }

    public void setCommands(Map<String, CommandProperties> commands) {
        this.commands = commands;
    }

    @Override
    public String toString() {
        return "\nid=" + id +
                ", data=" + data +
                ", measurements=" + measurements +
                ", commands=" + commands;
    }
}
