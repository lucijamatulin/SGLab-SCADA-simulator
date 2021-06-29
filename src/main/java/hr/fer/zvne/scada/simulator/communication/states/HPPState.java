package hr.fer.zvne.scada.simulator.communication.states;

public class HPPState implements StateInterface {
    private double activePower;
    private double reactivePower;
    private double apparentPower;
    private String state;

    public HPPState() {
    }

    public HPPState(double activePower, double reactivePower, double apparentPower, String state) {
        this.activePower = activePower;
        this.reactivePower = reactivePower;
        this.apparentPower = apparentPower;
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

    public synchronized String getState() {
        return state;
    }

    public synchronized void setState(String state) {
        this.state = state;
    }
}
