package hr.fer.zvne.scada.simulator.config;

import java.util.Map;

public class MachineProperties {
    private String key;
    private String dataType;
    private Map<String, Double> bounds;
    private Double rampUp;
    private Double rampDown;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Map<String, Double> getBounds() {
        return bounds;
    }

    public void setBounds(Map<String, Double> bounds) {
        this.bounds = bounds;
    }

    public Double getRampUp() {
        return rampUp;
    }

    public void setRampUp(Double rampUp) {
        this.rampUp = rampUp;
    }

    public Double getRampDown() {
        return rampDown;
    }

    public void setRampDown(Double rampDown) {
        this.rampDown = rampDown;
    }

    @Override
    public String toString() {
        return "MachineProperties{" +
                "key='" + key + '\'' +
                ", dataType='" + dataType + '\'' +
                ", bounds=" + bounds +
                ", rampUp=" + rampUp +
                ", rampDown=" + rampDown +
                '}';
    }
}
