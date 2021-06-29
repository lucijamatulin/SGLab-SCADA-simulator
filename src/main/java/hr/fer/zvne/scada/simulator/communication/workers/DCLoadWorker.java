package hr.fer.zvne.scada.simulator.communication.workers;

import hr.fer.zvne.scada.simulator.communication.DeviceRegistry;
import hr.fer.zvne.scada.simulator.communication.states.DCLoadState;
import hr.fer.zvne.scada.simulator.config.MachineModel;
import hr.fer.zvne.scada.simulator.models.Message;

import java.util.concurrent.BlockingQueue;

public class DCLoadWorker extends Thread implements WorkerInterface {
    private MachineModel machineModel;
    private DCLoadState dcLoadState;
    private BlockingQueue<Message> queue;

    public DCLoadWorker(MachineModel machineModel, BlockingQueue<Message> queue) {
        this.machineModel = machineModel;
        this.dcLoadState = new DCLoadState();
        this.queue = queue;
    }

    @Override
    public void run() {
    }

    @Override
    public void processCommand(String key, Object value, DeviceRegistry deviceRegistry) {
        if (key.equals("Sim.Mjerenja2:148") && value.equals(true)) {
            System.out.println("iref");
        }
        else if (key.equals("Sim.Mjerenja2:149") && value.equals(true)) {
            System.out.println("pref");
        } else if (key.equals("Sim.Mjerenja2:152") && value.equals(true)) {
            System.out.println("activate");
        } else if (key.equals("Sim.Mjerenja2:150") && value.equals(true)) {
            System.out.println("iref");
        } else if (key.equals("Sim.Mjerenja2:151") && value.equals(true)) {
            System.out.println("pref");
        } else if (key.equals("Sim.Mjerenja2:153") && value.equals(true)) {
            System.out.println("activate");
        } else {
            System.out.println("Do nothing.");
        }
    }
}
