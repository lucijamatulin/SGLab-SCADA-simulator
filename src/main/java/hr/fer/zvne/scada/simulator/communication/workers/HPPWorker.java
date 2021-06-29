package hr.fer.zvne.scada.simulator.communication.workers;

import hr.fer.zvne.scada.simulator.communication.DeviceRegistry;
import hr.fer.zvne.scada.simulator.communication.states.HPPState;
import hr.fer.zvne.scada.simulator.config.MachineModel;
import hr.fer.zvne.scada.simulator.models.Data;
import hr.fer.zvne.scada.simulator.models.Message;
import hr.fer.zvne.scada.simulator.models.messages.DataChangedUnsolicitedBody;
import hr.fer.zvne.scada.simulator.models.values.Quality;
import hr.fer.zvne.scada.simulator.models.values.Source;
import hr.fer.zvne.scada.simulator.models.values.Type;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class HPPWorker extends Thread implements WorkerInterface {
    private MachineModel machineModel;
    private HPPState hppState = new HPPState(0.0, 0.0, 0.0, "sleeping");
    private BlockingQueue<Message> queue;
    private Map<String, String> keyToCommandName = new HashMap<>();

    public HPPWorker(MachineModel machineModel, BlockingQueue<Message> queue) {
        this.machineModel = machineModel;
        this.queue = queue;
        initMappings();

    }

    @Override
    public void run(){
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                queue.put(new Message("data_changed_unsolicited", new DataChangedUnsolicitedBody(
                        new Data[]{new Data("Sim.Mjerenja1:97",
                                generateMeasurement(hppState, hppState.getActivePower()),
                                Quality.GOOD.name(),
                                Timestamp.valueOf(LocalDateTime.now()),
                                Type.EVENT.name(), Source.APPLICATION.name()),
                                new Data("Sim.Mjerenja1:98",
                                        generateMeasurement(hppState, hppState.getReactivePower()),
                                        Quality.GOOD.name(),
                                        Timestamp.valueOf(LocalDateTime.now()),
                                        Type.EVENT.name(), Source.APPLICATION.name()),
                                new Data("Sim.Mjerenja1:99",
                                        generateMeasurement(hppState, hppState.getApparentPower()),
                                        Quality.GOOD.name(),
                                        Timestamp.valueOf(LocalDateTime.now()),
                                        Type.EVENT.name(), Source.APPLICATION.name())})));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void processCommand(String key, Object value, DeviceRegistry deviceRegistry) {
        Double measurement;
        Double rangeMin = machineModel.getMeasurements().get("activePower").getBounds().get("min");
        Double rangeMax = machineModel.getMeasurements().get("activePower").getBounds().get("max");
        Double ramp = 0.8;
        String commandName = keyToCommandName.get(key);
        String valueType = value.getClass().getSimpleName();

        if (valueType.equals("String") && (value.equals("true") || value.equals("false"))) {
            value = Boolean.valueOf(value.toString());
        }
        if (valueType.equals("Integer")) {
            value = Double.valueOf(value.toString());
        }


        switch (commandName) {
            case "start":
                Random random = new Random();
                measurement = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
//                deviceRegistry.startWorker(1);
                hppState.setState("working");
                hppState.setActivePower(generateMeasurement(hppState, rangeMin));
                hppState.setApparentPower(hppState.getActivePower() / 0.5);
                hppState.setReactivePower(Math.sqrt(Math.pow(hppState.getApparentPower(), 2) -
                        Math.pow(hppState.getActivePower(), 2)));
                break;
            case "stop":
                hppState.setState("turning off");
                changeValue(hppState, 0.0);
                hppState.setState("sleeping");
                System.out.println("Measurement generation stoped.");
                break;
            case "pref":
                changeValue(hppState, (Double) value);
                break;
            default:
                System.out.println("Unknown key: " + key);
        }
    }

    public Double generateMeasurement(HPPState hppState, Double setPoint) {
        Double measurement = null;
        Random random = new Random();
        measurement = setPoint + random.nextGaussian() * 0.02;
        if (hppState.getState().equals("sleeping")  || measurement < 0.0) {
            return 0.0;
        }
        return measurement;
    }

    public void changeValue(HPPState state, Double setPoint) {
        Double measurement = null;
        Random random = new Random();
        Double ramp = 0.8;
        if (hppState.getActivePower() < setPoint) {
            while (hppState.getActivePower() < setPoint) {
                measurement = hppState.getActivePower() + random.nextGaussian() * 0.02 + ramp;
                state.setActivePower(measurement);
                state.setApparentPower(state.getActivePower() / 0.5);
                state.setReactivePower(Math.sqrt(Math.pow(state.getApparentPower(), 2) -
                        Math.pow(state.getActivePower(), 2)));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } if ((hppState.getActivePower() > setPoint) && (hppState.getActivePower() - setPoint <= ramp)) {
                state.setActivePower(setPoint);
                state.setApparentPower(state.getActivePower() / 0.5);
                state.setReactivePower(Math.sqrt(Math.pow(state.getApparentPower(), 2) -
                        Math.pow(state.getActivePower(), 2)));
            }
        }
        if (hppState.getActivePower() > setPoint) {
            while (hppState.getActivePower() > setPoint) {
                measurement = hppState.getActivePower() + random.nextGaussian() * 0.02 - ramp;
                state.setActivePower(measurement);
                state.setApparentPower(state.getActivePower() / 0.5);
                state.setReactivePower(Math.sqrt(Math.pow(state.getApparentPower(), 2) -
                        Math.pow(state.getActivePower(), 2)));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } if ((hppState.getActivePower() < setPoint) && (setPoint - hppState.getActivePower() <= ramp)) {
                state.setActivePower(setPoint);
                state.setApparentPower(state.getActivePower() / 0.5);
                state.setReactivePower(Math.sqrt(Math.pow(state.getApparentPower(), 2) -
                        Math.pow(state.getActivePower(), 2)));
            }
        }

    }

    private void initMappings() {
        if (machineModel.getData().get("controllableUnit").equals("true")) {
            machineModel.getCommands().forEach((key, value) -> keyToCommandName.put(value.getKey(), key));
        }
    }
}

