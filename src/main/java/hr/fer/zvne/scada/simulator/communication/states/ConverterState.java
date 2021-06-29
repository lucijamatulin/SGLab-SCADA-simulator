package hr.fer.zvne.scada.simulator.communication.states;

public class ConverterState implements StateInterface {
    private double activePower;
    private double voltage;
    private double current;
    private String state;

    public ConverterState() {
    }

    public ConverterState(double activePower, double voltage, double current, String state) {
        this.activePower = activePower;
        this.voltage = voltage;
        this.current = current;
        this.state = state;
    }

    public synchronized double getActivePower() {
        return activePower;
    }

    public synchronized void setActivePower(double activePower) {
        this.activePower = activePower;
    }

    public synchronized double getVoltage() {
        return voltage;
    }

    public synchronized void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public synchronized double getCurrent() {
        return current;
    }

    public synchronized void setCurrent(double current) {
        this.current = current;
    }

    public synchronized String getState() {
        return state;
    }

    public synchronized void setState(String state) {
        this.state = state;
    }
}
