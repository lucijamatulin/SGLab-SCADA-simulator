package hr.fer.zvne.scada.simulator.communication.states;

public class PVState implements StateInterface {
    private double activePower;
    private double reactivePower;
    private double apparentPower;
    private double activePowerLimitation;
    private double totalEnergyFed;
    private double currentDayTotalEnergyFed;
    private String state;

    public PVState() {
    }

    public PVState(double activePower, double reactivePower, double apparentPower,
                   double activePowerLimitation, double totalEnergyFed,
                   double currentDayTotalEnergyFed, String state) {
        this.activePower = activePower;
        this.reactivePower = reactivePower;
        this.apparentPower = apparentPower;
        this.activePowerLimitation = activePowerLimitation;
        this.totalEnergyFed = totalEnergyFed;
        this.currentDayTotalEnergyFed = currentDayTotalEnergyFed;
        this.state = state;
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

    public synchronized double getActivePowerLimitation() {
        return activePowerLimitation;
    }

    public synchronized void setActivePowerLimitation(double activePowerLimitation) {
        this.activePowerLimitation = activePowerLimitation;
    }

    public synchronized double getTotalEnergyFed() {
        return totalEnergyFed;
    }

    public synchronized void setTotalEnergyFed(double totalEnergyFed) {
        this.totalEnergyFed = totalEnergyFed;
    }

    public synchronized double getCurrentDayTotalEnergyFed() {
        return currentDayTotalEnergyFed;
    }

    public synchronized void setCurrentDayTotalEnergyFed(double currentDayTotalEnergyFed) {
        this.currentDayTotalEnergyFed = currentDayTotalEnergyFed;
    }

    public synchronized String getState() {
        return state;
    }

    public synchronized void setState(String state) {
        this.state = state;
    }
}
