package hr.fer.zvne.scada.simulator.communication.states;

public class GenericState {
    private double activePower;
    private double reactivePower;
    private double apparentPower;
    private double activePowerLimitation;
    private double currentDayTotalEnergyFed;
    private double totalEnergyFed;
    private double current;
    private double voltage;
    private double stateOfCharge;
    private String machineStatus;

    public GenericState() {
    }

    public GenericState(double activePower, String machineStatus) {
        this.activePower = activePower;
        this.machineStatus = machineStatus;
    }

    public GenericState(double activePower, double reactivePower, double apparentPower, double activePowerLimitation, double currentDayTotalEnergyFed, double totalEnergyFed, double current, double voltage, double stateOfCharge, String machineStatus) {
        this.activePower = activePower;
        this.reactivePower = reactivePower;
        this.apparentPower = apparentPower;
        this.activePowerLimitation = activePowerLimitation;
        this.currentDayTotalEnergyFed = currentDayTotalEnergyFed;
        this.totalEnergyFed = totalEnergyFed;
        this.current = current;
        this.voltage = voltage;
        this.stateOfCharge = stateOfCharge;
        this.machineStatus = machineStatus;
    }

    public synchronized double getActivePower() {
        return activePower;
    }

    public synchronized void setActivePower(double activePower) {
        this.activePower = activePower;
    }

    public synchronized double getReactivePower() {
        return reactivePower;
    }

    public synchronized void setReactivePower(double reactivePower) {
        this.reactivePower = reactivePower;
    }

    public synchronized double getApparentPower() {
        return apparentPower;
    }

    public synchronized void setApparentPower(double apparentPower) {
        this.apparentPower = apparentPower;
    }

    public String getMachineStatus() {
        return machineStatus;
    }

    public void setMachineStatus(String machineStatus) {
        this.machineStatus = machineStatus;
    }

    public double getActivePowerLimitation() {
        return activePowerLimitation;
    }

    public void setActivePowerLimitation(double activePowerLimitation) {
        this.activePowerLimitation = activePowerLimitation;
    }

    public double getCurrentDayTotalEnergyFed() {
        return currentDayTotalEnergyFed;
    }

    public void setCurrentDayTotalEnergyFed(double currentDayTotalEnergyFed) {
        this.currentDayTotalEnergyFed = currentDayTotalEnergyFed;
    }

    public double getTotalEnergyFed() {
        return totalEnergyFed;
    }

    public void setTotalEnergyFed(double totalEnergyFed) {
        this.totalEnergyFed = totalEnergyFed;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public double getStateOfCharge() {
        return stateOfCharge;
    }

    public void setStateOfCharge(double stateOfCharge) {
        this.stateOfCharge = stateOfCharge;
    }
}
