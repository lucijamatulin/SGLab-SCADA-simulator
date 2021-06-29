package hr.fer.zvne.scada.simulator.config;

import java.util.List;

public class ConfigurationModel {
    public ConfigurationModel() {
    }
    private String version;
    private Info info;
    private Authentication authentication;
    private Communication communication;
    private List<MachineModel> machines;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public Communication getCommunication() {
        return communication;
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    public List<MachineModel> getMachines() {
        return machines;
    }

    public void setMachines(List<MachineModel> machines) {
        this.machines = machines;
    }

    @Override
    public String toString() {
        return "ConfigurationModel{" +
                "version='" + version + '\'' +
                ", info=" + info +
                ", authentication=" + authentication +
                ", communication=" + communication +
                ", machines=" + machines +
                '}';
    }
}
