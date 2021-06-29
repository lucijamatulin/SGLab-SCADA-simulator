package hr.fer.zvne.scada.simulator.communication;

import hr.fer.zvne.scada.simulator.communication.workers.WorkerInterface;
import hr.fer.zvne.scada.simulator.config.CommandProperties;
import hr.fer.zvne.scada.simulator.config.ConfigurationModel;
import hr.fer.zvne.scada.simulator.config.MachineProperties;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RoutingHandler {
    private ConfigurationModel configurationModel;
    private DeviceRegistry deviceRegistry;
    private Map<String, Integer> measurementToDevice = new HashMap<>();
    private Map<Integer, Set<String>> deviceToMeasurement = new HashMap<>();
    private Map<String, Integer> commandToDevice = new HashMap<>();
    private Map<Integer, Set<String>> deviceToCommand = new HashMap<>();

    public RoutingHandler(ConfigurationModel configurationModel, DeviceRegistry deviceRegistry) {
        this.configurationModel = configurationModel;
        this.deviceRegistry = deviceRegistry;
        measurementDevicePairsInit();
        commandDevicePairsInit();
    }

    public ConfigurationModel getConfigurationModel() {
        return configurationModel;
    }

    public void setConfigurationModel(ConfigurationModel configurationModel) {
        this.configurationModel = configurationModel;
    }

    public Map<String, Integer> getMeasurementToDevice() {
        return measurementToDevice;
    }

    public void setMeasurementToDevice(Map<String, Integer> measurementToDevice) {
        this.measurementToDevice = measurementToDevice;
    }

    public Map<Integer, Set<String>> getDeviceToMeasurement() {
        return deviceToMeasurement;
    }

    public void setDeviceToMeasurement(Map<Integer, Set<String>> deviceToMeasurement) {
        this.deviceToMeasurement = deviceToMeasurement;
    }

    public Map<String, Integer> getCommandToDevice() {
        return commandToDevice;
    }

    public void setCommandToDevice(Map<String, Integer> commandToDevice) {
        this.commandToDevice = commandToDevice;
    }

    public Map<Integer, Set<String>> getDeviceToCommand() {
        return deviceToCommand;
    }

    public void setDeviceToCommand(Map<Integer, Set<String>> deviceToCommand) {
        this.deviceToCommand = deviceToCommand;
    }

    @Override
    public String toString() {
        return "RoutingHandler{" +
                "configurationModel=" + configurationModel +
                ", measurementToDevice=" + measurementToDevice +
                ", deviceToMeasurement=" + deviceToMeasurement +
                ", commandToDevice=" + commandToDevice +
                ", deviceToCommand=" + deviceToCommand +
                '}';
    }


    private void measurementDevicePairsInit() {
        for (int i = 0; i < configurationModel.getMachines().size(); i++) {
            Set<String> keys = new HashSet<>();
            for (Map.Entry<String, MachineProperties> entry :
                    configurationModel.getMachines().get(i).getMeasurements().entrySet()) {
                measurementToDevice.put(entry.getValue().getKey(), configurationModel.getMachines().get(i).getId());
                keys.add(entry.getValue().getKey());
            }
            deviceToMeasurement.put(configurationModel.getMachines().get(i).getId(), keys);
        }
    }

    private void commandDevicePairsInit() {
        for (int i = 0; i < configurationModel.getMachines().size(); i++) {
            Set<String> keys = new HashSet<>();
            if (configurationModel.getMachines().get(i).getData().containsValue("true")) {
                for (Map.Entry<String, CommandProperties> entry :
                        configurationModel.getMachines().get(i).getCommands().entrySet()) {
                    commandToDevice.put(entry.getValue().getKey(), configurationModel.getMachines().get(i).getId());
                    keys.add(entry.getValue().getKey());
                }
                deviceToCommand.put(configurationModel.getMachines().get(i).getId(), keys);
            }
        }
    }

    public Integer getDevice(String key) {
        Integer id;
        if (measurementToDevice.containsKey(key)) {
            id = measurementToDevice.getOrDefault(key, -1);
        } else id = commandToDevice.getOrDefault(key, -1);
        return id;
    }

    public WorkerInterface route(String key, DeviceRegistry deviceRegistry) {
        return deviceRegistry.getWorker(getDevice(key));
    }
}
