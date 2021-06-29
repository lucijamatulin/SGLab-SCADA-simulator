package hr.fer.zvne.scada.simulator.communication.workers;

import hr.fer.zvne.scada.simulator.communication.DeviceRegistry;
import hr.fer.zvne.scada.simulator.communication.states.ConverterState;
import hr.fer.zvne.scada.simulator.config.MachineModel;
import hr.fer.zvne.scada.simulator.models.Message;

import java.util.concurrent.BlockingQueue;


public class ConverterWorker extends Thread implements WorkerInterface {
    private MachineModel machineModel;
    private ConverterState converterState;
    private BlockingQueue<Message> queue;

    public ConverterWorker(MachineModel machineModel, BlockingQueue<Message> queue) {
        this.machineModel = machineModel;
        this.converterState = new ConverterState();
        this.queue = queue;
    }

    @Override
    public void run() {
    }

    @Override
    public void processCommand(String key, Object value, DeviceRegistry deviceRegistry) {
        if (key.equals("Sim.Mjerenja2:139") && value.equals(true)) {
            System.out.println("uref");
        }
        else if (key.equals("Sim.Mjerenja2:141") && value.equals(true)) {
            System.out.println("pref");
        } else if (key.equals("Sim.Mjerenja2:135") && value.equals(true)) {
            System.out.println("activate");
        } else if (key.equals("Sim.Mjerenja2:183") && value.equals(true)) {
            System.out.println("discharge");
        } else {
            System.out.println("Do nothing.");
        }
    }
}
