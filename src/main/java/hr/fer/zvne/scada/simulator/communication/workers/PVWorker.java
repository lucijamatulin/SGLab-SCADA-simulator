package hr.fer.zvne.scada.simulator.communication.workers;

import hr.fer.zvne.scada.simulator.communication.DeviceRegistry;
import hr.fer.zvne.scada.simulator.communication.states.PVState;
import hr.fer.zvne.scada.simulator.config.MachineModel;
import hr.fer.zvne.scada.simulator.models.Message;

import java.util.concurrent.BlockingQueue;

public class PVWorker extends Thread implements WorkerInterface {
    private MachineModel machineModel;
    private PVState pvState;
    private BlockingQueue<Message> queue;

    public PVWorker(MachineModel machineModel, BlockingQueue<Message> queue) {
        this.machineModel = machineModel;
        this.pvState = new PVState();
        this.queue = queue;
    }

    @Override
    public void run() {
    }

    @Override
    public void processCommand(String key, Object value, DeviceRegistry deviceRegistry) {
        System.out.println("Non-controllable unit");
    }
}