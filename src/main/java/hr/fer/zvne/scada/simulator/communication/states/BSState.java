package hr.fer.zvne.scada.simulator.communication.states;

public class BSState implements StateInterface {
    private double activePower;
    private double apparentPower;
    private double stateOfCharge;
    private String state;

    public BSState() {
    }

    public BSState(double activePower, double apparentPower, double stateOfCharge, String state) {
        this.activePower = activePower;
        this.apparentPower = apparentPower;
        this.stateOfCharge = stateOfCharge;
        this.state = state;
    }

    public synchronized double getActivePower() {
        return activePower;
    }

    public synchronized void setActivePower(double activePower) {
        this.activePower = activePower;
    }

    public synchronized double getApparentPower() {
        return apparentPower;
    }

    public synchronized void setApparentPower(double apparentPower) {
        this.apparentPower = apparentPower;
    }

    public synchronized double getStateOfCharge() {
        return stateOfCharge;
    }

    public synchronized void setStateOfCharge(double stateOfCharge) {
        this.stateOfCharge = stateOfCharge;
    }

    public synchronized String getState() {
        return state;
    }

    public synchronized void setState(String state) {
        this.state = state;
    }
}
