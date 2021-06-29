package hr.fer.zvne.scada.simulator.communication.workers;

import hr.fer.zvne.scada.simulator.communication.DeviceRegistry;
import hr.fer.zvne.scada.simulator.communication.states.GenericState;
import hr.fer.zvne.scada.simulator.communication.states.MachineStatus;
import hr.fer.zvne.scada.simulator.config.MachineModel;
import hr.fer.zvne.scada.simulator.models.Data;
import hr.fer.zvne.scada.simulator.models.Message;
import hr.fer.zvne.scada.simulator.models.messages.DataChangedUnsolicitedBody;
import hr.fer.zvne.scada.simulator.models.values.Quality;
import hr.fer.zvne.scada.simulator.models.values.Source;
import hr.fer.zvne.scada.simulator.models.values.Type;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

public class GenericWorker extends Thread implements WorkerInterface {
    private MachineModel machineModel;
    private GenericState state = new GenericState(0.0, MachineStatus.OFF.name());
    private BlockingQueue<Message> queue;
    private Map<String, String> keyToCommandName = new HashMap<>();
    private Double rangeMinActivePower, rangeMaxActivePower, rampUp, rampDown, rangeMinVoltage, rangeMaxVoltage,
            rangeMinCurrent, rangeMaxCurrent, rangeMinSoC, rangeMaxSoC, powerFactor, averageDailyProduction,
            averageYearlyProduction, nominalCapacity;
    private Double[] averageHourlyProduction = new Double[24];
    private int machineId;

    public GenericWorker(MachineModel machineModel, BlockingQueue<Message> queue) {
        this.machineModel = machineModel;
        this.queue = queue;
        machineParametersInit();
        stateInit();
        mappingsInit();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                switch (machineId) {
                    case 1:
                        createUnsolicitedMessage(machineModel.getMeasurements().get("activePower").getKey(),
                                machineModel.getMeasurements().get("reactivePower").getKey(),
                                machineModel.getMeasurements().get("apparentPower").getKey(),
                                state.getActivePower(), state.getReactivePower(), state.getApparentPower());
                        break;
                    case 2:
                        createUnsolicitedMessage(machineModel.getMeasurements().get("activePower").getKey(),
                                machineModel.getMeasurements().get("reactivePower").getKey(),
                                machineModel.getMeasurements().get("apparentPower").getKey(),
                                state.getActivePower(), state.getReactivePower(), state.getApparentPower());
                        createUnsolicitedMessage(machineModel.getMeasurements().get("activePowerLimitation").getKey(),
                                machineModel.getMeasurements().get("totalEnergyFed").getKey(),
                                machineModel.getMeasurements().get("currentDayTotalEnergyFed").getKey(),
                                state.getActivePowerLimitation(), state.getTotalEnergyFed(), state.getCurrentDayTotalEnergyFed());
                        break;
                    case 3:
                    case 4:
                    case 5:
                        createUnsolicitedMessage(machineModel.getMeasurements().get("activePower").getKey(),
                                machineModel.getMeasurements().get("current").getKey(),
                                machineModel.getMeasurements().get("voltage").getKey(),
                                state.getActivePower(), state.getCurrent(), state.getVoltage());
                        break;
                    case 6:
                        createUnsolicitedMessage(machineModel.getMeasurements().get("activePower").getKey(),
                                machineModel.getMeasurements().get("apparentPower").getKey(),
                                machineModel.getMeasurements().get("stateOfCharge").getKey(),
                                state.getActivePower(), state.getApparentPower(), state.getStateOfCharge());
                        break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void createUnsolicitedMessage(String s1, String s2, String s3, double value1, double value2, double value3) throws InterruptedException {
        queue.put(new Message("data_changed_unsolicited", new DataChangedUnsolicitedBody(
                new Data[]{new Data(s1,
                        generateMeasurement(state, value1),
                        Quality.GOOD.name(),
                        Timestamp.valueOf(LocalDateTime.now()),
                        Type.EVENT.name(), Source.APPLICATION.name()),
                        new Data(s2,
                                generateMeasurement(state, value2),
                                Quality.GOOD.name(),
                                Timestamp.valueOf(LocalDateTime.now()),
                                Type.EVENT.name(), Source.APPLICATION.name()),
                        new Data(s3,
                                generateMeasurement(state, value3),
                                Quality.GOOD.name(),
                                Timestamp.valueOf(LocalDateTime.now()),
                                Type.EVENT.name(), Source.APPLICATION.name())})));
        return;
    }

    @Override
    public void processCommand(String key, Object value, DeviceRegistry deviceRegistry) {
        Double measurement;
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
                if (value.equals(true)) {
                    state.setMachineStatus(MachineStatus.ON.name());
                    state.setActivePower(generateMeasurement(state, rangeMinActivePower));
                    state.setApparentPower(state.getActivePower() / powerFactor);
                    state.setReactivePower(Math.sqrt(Math.pow(state.getApparentPower(), 2) -
                            Math.pow(state.getActivePower(), 2)));
                }
                break;
            case "activate":
                switch (machineId) {
                    case 6:
                        if (value.equals(true)) {
                            Random random = new Random();
                            Double SoC = rangeMinSoC + (rangeMaxSoC - rangeMinSoC) * random.nextDouble();
                            powerFactor = powerFactor + (1.0 - powerFactor) * random.nextDouble();
                            state.setMachineStatus(MachineStatus.ON.name());
                            state.setActivePower(generateMeasurement(state, rangeMinActivePower));
                            state.setApparentPower(state.getActivePower() / powerFactor);
                            state.setStateOfCharge(generateMeasurement(state, SoC));
                        } else {
                            state.setMachineStatus(MachineStatus.TURNING_OFF.name());
                            changePowerValue(state, 0.0);
                            state.setMachineStatus(MachineStatus.OFF.name());
                        }
                        break;
                    default:
                        if (value.equals(true)) {
                            Random random = new Random();
                            measurement = rangeMinVoltage + (rangeMaxVoltage - rangeMinVoltage) * random.nextDouble();
                            state.setMachineStatus(MachineStatus.ON.name());
                            Double activePowerMeasurement = generateMeasurement(state, rangeMinActivePower);
                            Double voltageMeasurement = generateMeasurement(state, measurement);
                            state.setActivePower(activePowerMeasurement);
                            state.setVoltage(voltageMeasurement);
                            state.setCurrent((activePowerMeasurement * 1000) / voltageMeasurement);
                        } else {
                            state.setMachineStatus(MachineStatus.TURNING_OFF.name());
                            changePowerValue(state, 0.0);
                            changeVoltageValue(state, 0.0);
                            changeCurrentValue(state, 0.0);
                            state.setMachineStatus(MachineStatus.OFF.name());
                        }
                }
                break;
            case "discharge":
                switch (machineId) {
                    case 6:
                        if (value.equals(true)) {
                            changeSoCValue(state, 0.0);
                        }
                        changePowerValue(state, 0.0);
                        state.setMachineStatus(MachineStatus.OFF.name());
                        break;
                    default:
                        break;
                }
                break;
            case "stop":
                if (value.equals(true)) {
                    state.setMachineStatus(MachineStatus.TURNING_OFF.name());
                    changePowerValue(state, 0.0);
                    state.setMachineStatus(MachineStatus.OFF.name());
                }
                break;
            case "pref":
                changePowerValue(state, (Double) value);
                break;
            case "uref":
                changeVoltageValue(state, (Double) value);
                break;
            case "iref":
                changeCurrentValue(state, (Double) value);
                break;
            default:
                System.out.println("Unknown key: " + key);
        }
    }

    public Double generateMeasurement(GenericState state, Double setPoint) {
        Double measurement = null;
        Random random = new Random();
        if ((state.getMachineStatus().equals(MachineStatus.OFF.name())) || (setPoint == 0.0)) {
            return 0.0;
        }
        do {
            measurement = setPoint + random.nextGaussian() * 0.02;
        } while ((measurement < 0.0) && !(state.getMachineStatus().equals(MachineStatus.OFF.name())));
        return measurement;
    }

    public void changePowerValue(GenericState state, Double setPoint) {
        Double measurement = null;
        Random random = new Random();
        if (setPoint < rangeMinActivePower) {
            setPoint = rangeMinActivePower;
        } else if (setPoint > rangeMaxActivePower) {
            setPoint = rangeMaxActivePower;
        }
        if (state.getActivePower() < setPoint) {
            while (state.getActivePower() < setPoint) {
                measurement = state.getActivePower() + random.nextGaussian() * 0.02 + rampUp;
                state.setActivePower(measurement);
                switch (machineId) {
                    case 1:
                        state.setApparentPower(state.getActivePower() / powerFactor);
                        state.setReactivePower(Math.sqrt(Math.pow(state.getApparentPower(), 2) -
                                Math.pow(state.getActivePower(), 2)));
                        break;
                    case 3:
                    case 4:
                    case 5:
                        state.setCurrent((state.getActivePower() * 1000) / state.getVoltage());
                        break;
                    case 6:
                        state.setApparentPower(state.getActivePower() / powerFactor);
                        break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if ((state.getActivePower() > setPoint) && (state.getActivePower() - setPoint <= rampDown)) {
                state.setActivePower(setPoint);
                switch (machineId) {
                    case 1:
                        state.setApparentPower(state.getActivePower() / powerFactor);
                        state.setReactivePower(Math.sqrt(Math.pow(state.getApparentPower(), 2) -
                                Math.pow(state.getActivePower(), 2)));
                        break;
                    case 3:
                    case 4:
                    case 5:
                        state.setCurrent((state.getActivePower() * 1000) / state.getVoltage());
                        break;
                    case 6:
                        state.setApparentPower(state.getActivePower() / powerFactor);
                        break;
                }
            }
        }
        if (state.getActivePower() > setPoint) {
            while ((state.getActivePower() > setPoint) && (state.getActivePower() - setPoint >= rampDown)) {
                measurement = state.getActivePower() + random.nextGaussian() * 0.02 - rampDown;
                state.setActivePower(measurement);
                switch (machineId) {
                    case 1:
                        state.setApparentPower(state.getActivePower() / powerFactor);
                        state.setReactivePower(Math.sqrt(Math.pow(state.getApparentPower(), 2) -
                                Math.pow(state.getActivePower(), 2)));
                        break;
                    case 3:
                    case 4:
                    case 5:
                        state.setCurrent((state.getActivePower() * 1000) / state.getVoltage());
                        break;
                    case 6:
                        state.setApparentPower(state.getActivePower() / powerFactor);
                        break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (state.getActivePower() - setPoint <= rampDown) {
                state.setActivePower(setPoint);
                switch (machineId) {
                    case 1:
                        state.setApparentPower(state.getActivePower() / powerFactor);
                        state.setReactivePower(Math.sqrt(Math.pow(state.getApparentPower(), 2) -
                                Math.pow(state.getActivePower(), 2)));
                        break;
                    case 3:
                    case 4:
                    case 5:
                        state.setCurrent((state.getActivePower() * 1000) / state.getVoltage());
                        break;
                    case 6:
                        state.setApparentPower(state.getActivePower() / powerFactor);
                        break;
                }
            }
        }
    }


    public void changeCurrentValue(GenericState state, Double setPoint) {
        Double measurement = null;
        Random random = new Random();

        if (setPoint < rangeMinCurrent) {
            setPoint = rangeMinCurrent;
        } else if (setPoint > rangeMaxCurrent) {
            setPoint = rangeMaxCurrent;
        }

        measurement = setPoint + random.nextGaussian() * 0.02;
        state.setCurrent(measurement);
        state.setVoltage((state.getActivePower() * 1000) / state.getCurrent());
    }

    public void changeVoltageValue(GenericState state, Double setPoint) {
        Double measurement = null;
        Random random = new Random();

        if (setPoint < rangeMinVoltage) {
            setPoint = rangeMinVoltage;
        } else if (setPoint > rangeMaxVoltage) {
            setPoint = rangeMaxVoltage;
        }

        measurement = setPoint + random.nextGaussian() * 0.02;
        state.setVoltage(measurement);
        state.setCurrent((state.getActivePower() * 1000) / state.getVoltage());
    }

    public void changeSoCValue(GenericState state, Double setPoint) {
        while ((state.getStateOfCharge() * nominalCapacity / 100) > setPoint) {
            Double measurement = state.getStateOfCharge() - (state.getActivePower() * 100) / (3600 * nominalCapacity);
            if (measurement <= setPoint) {
                state.setStateOfCharge(setPoint);
                break;
            }
            state.setStateOfCharge(measurement);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void stateInit() {
        switch (machineId) {
            case 1:
                state.setReactivePower(0.0);
                state.setApparentPower(0.0);
                break;
            case 2:
                generateSolarMeasurements();
                break;
            case 3:
            case 4:
            case 5:
                state.setCurrent(0.0);
                state.setVoltage(0.0);
                break;
            case 6:
                state.setApparentPower(0.0);
                state.setStateOfCharge(0.0);
                break;
        }
    }

    private void mappingsInit() {
        if (machineModel.getData().get("controllableUnit").equals("true")) {
            machineModel.getCommands().forEach((key, value) -> keyToCommandName.put(value.getKey(), key));
        }
    }

    private void machineParametersInit() {
        rangeMinActivePower = machineModel.getMeasurements().get("activePower").getBounds().get("min");
        rangeMaxActivePower = machineModel.getMeasurements().get("activePower").getBounds().get("max");
        rampUp = machineModel.getMeasurements().get("activePower").getRampUp();
        rampDown = machineModel.getMeasurements().get("activePower").getRampDown();
        machineId = machineModel.getId();
        switch (machineId) {
            case 1:
                powerFactor = Double.valueOf(machineModel.getData().get("powerFactor"));
                break;
            case 2:
                powerFactor = Double.valueOf(machineModel.getData().get("powerFactor"));
                averageDailyProduction = Double.valueOf(machineModel.getData().get("averageDailyOutput")) * 1000;
                averageYearlyProduction = Double.valueOf(machineModel.getData().get("averageYearlyOutput")) * 1000;
                String[] averageHourlyProfiles = machineModel.getData().get("averageHourlyProfiles").split(", ");
                IntStream.range(0, 24).forEach(i -> averageHourlyProduction[i] = Double.valueOf(averageHourlyProfiles[i]));
                break;
            case 3:
            case 4:
            case 5:
                rangeMinVoltage = machineModel.getMeasurements().get("voltage").getBounds().get("min");
                rangeMaxVoltage = machineModel.getMeasurements().get("voltage").getBounds().get("max");
                rangeMinCurrent = machineModel.getMeasurements().get("current").getBounds().get("min");
                rangeMaxCurrent = machineModel.getMeasurements().get("current").getBounds().get("max");
                break;
            case 6:
                powerFactor = Double.valueOf(machineModel.getData().get("powerFactor"));
                rangeMinSoC = machineModel.getMeasurements().get("stateOfCharge").getBounds().get("min");
                rangeMaxSoC = machineModel.getMeasurements().get("stateOfCharge").getBounds().get("max");
                nominalCapacity = Double.valueOf(machineModel.getData().get("nominalCapacity"));
                break;
        }
    }

    private void generateSolarMeasurements() {
        int hour = LocalDateTime.now().getHour();
        int month = LocalDateTime.now().getMonthValue();
        Random random = new Random();
        powerFactor = powerFactor + (1.0 - powerFactor) * random.nextDouble();

        state.setMachineStatus(MachineStatus.ON.name());
        state.setActivePower(generateMeasurement(state, averageHourlyProduction[hour]));
        state.setApparentPower(state.getActivePower() / powerFactor);
        state.setReactivePower(Math.sqrt(Math.pow(state.getApparentPower(), 2) -
                Math.pow(state.getActivePower(), 2)));
        state.setActivePowerLimitation(rangeMaxActivePower);
        state.setCurrentDayTotalEnergyFed(averageDailyProduction * hour / 24);
        state.setTotalEnergyFed(averageYearlyProduction * month / 12);
    }
}
