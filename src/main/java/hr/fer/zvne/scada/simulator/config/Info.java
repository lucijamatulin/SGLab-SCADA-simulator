package hr.fer.zvne.scada.simulator.config;

public class Info {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "\n\tname: " + name;
    }
}
