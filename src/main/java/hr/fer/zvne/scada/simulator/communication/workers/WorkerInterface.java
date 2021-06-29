package hr.fer.zvne.scada.simulator.communication.workers;

import hr.fer.zvne.scada.simulator.communication.DeviceRegistry;

public interface WorkerInterface {
    void run();
    void processCommand(String key, Object value, DeviceRegistry deviceRegistry);
}
