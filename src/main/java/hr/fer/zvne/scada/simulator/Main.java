package hr.fer.zvne.scada.simulator;

import hr.fer.zvne.scada.simulator.communication.GatewayHandler;
import hr.fer.zvne.scada.simulator.config.ConfigurationLoader;
import hr.fer.zvne.scada.simulator.config.ConfigurationModel;
import hr.fer.zvne.scada.simulator.web.ScadaSimulatorServer;

public class Main {

    public static void main(String[] args) throws Exception {
        ConfigurationModel configurationModel = null;
        try {
            configurationModel = new ConfigurationLoader().loadConfiguration(ConfigurationModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (configurationModel == null) {
            throw new RuntimeException();
        } else {
            System.out.println("Configuration loading finished.\n\n");
        }

        ScadaSimulatorServer server = new ScadaSimulatorServer(configurationModel);
        server.run();

        GatewayHandler gateway = new GatewayHandler(configurationModel);
        gateway.gatewayInit();
    }

}
