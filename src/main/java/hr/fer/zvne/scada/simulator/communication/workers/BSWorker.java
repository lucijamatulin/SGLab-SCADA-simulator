package hr.fer.zvne.scada.simulator.communication.workers;

import hr.fer.zvne.scada.simulator.communication.DeviceRegistry;
import hr.fer.zvne.scada.simulator.communication.states.BSState;
import hr.fer.zvne.scada.simulator.config.MachineModel;
import hr.fer.zvne.scada.simulator.models.Message;

import java.util.concurrent.BlockingQueue;

public class BSWorker extends Thread implements WorkerInterface {
    private MachineModel machineModel;
    private BSState bsState;
    private BlockingQueue<Message> queue;

    public BSWorker(MachineModel machineModel, BlockingQueue<Message> queue) {
        this.machineModel = machineModel;
        this.bsState = new BSState();
        this.queue = queue;
    }

    @Override
    public void run() {
    }

    @Override
    public void processCommand(String key, Object value, DeviceRegistry deviceRegistry) {
        if (key.equals("Sim.Mjerenja4:180") && value.equals(true)) {
            System.out.println("pref");
        }
        else if (key.equals("Sim.Mjerenja4:182") && value.equals(true)) {
            System.out.println("activate");
        } else if (key.equals("Sim.Mjerenja4:181") && value.equals(true)) {
            System.out.println("discharge");
        } else {
            System.out.println("Do nothing.");
        }
    }
}