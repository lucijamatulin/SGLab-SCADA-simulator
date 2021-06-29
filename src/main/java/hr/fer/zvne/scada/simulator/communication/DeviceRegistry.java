package hr.fer.zvne.scada.simulator.communication;

import hr.fer.zvne.scada.simulator.communication.workers.GenericWorker;
import hr.fer.zvne.scada.simulator.communication.workers.WorkerInterface;
import hr.fer.zvne.scada.simulator.config.ConfigurationModel;
import hr.fer.zvne.scada.simulator.config.MachineModel;
import hr.fer.zvne.scada.simulator.models.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class DeviceRegistry {
    private ConfigurationModel configurationModel;
    private Map<Integer, WorkerInterface> workerInterfaceMap = new HashMap<>();
    private Map<Integer, Thread> threadMap = new HashMap<>();
    private BlockingQueue<Message> queue;

    public DeviceRegistry(ConfigurationModel configurationModel, BlockingQueue<Message> queue) {
        this.configurationModel = configurationModel;
        this.queue = queue;
        devicesInit();
        startWorkers();
    }

    private void devicesInit() {
        for (MachineModel m : configurationModel.getMachines()) {
            workerInterfaceMap.put(m.getId(), new GenericWorker(m, queue));
        }
    }

    public void startWorker(Integer id) {
        Thread thread = new Thread((Runnable) workerInterfaceMap.get(id),
                configurationModel.getMachines().get(id - 1).getData().get("name"));
        thread.start();
    }

    public void startWorkers() {
        for (int i = 0; i < configurationModel.getMachines().size(); i++) {
            Thread thread = new Thread((Runnable) workerInterfaceMap.get(i + 1),
                    configurationModel.getMachines().get(i).getData().get("name"));
            thread.start();
            threadMap.put(i + 1, thread);
        }
    }

    public WorkerInterface getWorker(Integer id) {
        return workerInterfaceMap.get(id);
    }

    public Thread getThread(Integer id) {return threadMap.get(id); }

    public ConfigurationModel getConfigurationModel() {
        return configurationModel;
    }

    public void setConfigurationModel(ConfigurationModel configurationModel) {
        this.configurationModel = configurationModel;
    }

    public Map<Integer, WorkerInterface> getWorkerInterfaceMap() {
        return workerInterfaceMap;
    }

    public void setWorkerInterfaceMap(Map<Integer, WorkerInterface> workerInterfaceMap) {
        this.workerInterfaceMap = workerInterfaceMap;
    }

    public Map<Integer, Thread> getThreadMap() {
        return threadMap;
    }

    public void setThreadMap(Map<Integer, Thread> threadMap) {
        this.threadMap = threadMap;
    }

    public BlockingQueue<Message> getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue<Message> queue) {
        this.queue = queue;
    }
}
