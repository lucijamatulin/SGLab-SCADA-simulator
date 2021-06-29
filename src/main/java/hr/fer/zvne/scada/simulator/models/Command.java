package hr.fer.zvne.scada.simulator.models;

public class Command {
    private String key;
    private Object value;

    public Command() {}

    public Command(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Command{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
