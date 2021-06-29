package hr.fer.zvne.scada.simulator.web;

import hr.fer.zvne.scada.simulator.config.ConfigurationModel;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScadaSimulatorServer {
    private static final Logger logger = LoggerFactory.getLogger(ScadaSimulatorServer.class);

    private final ConfigurationModel configurationModel;
    private Javalin app;

    public ScadaSimulatorServer(ConfigurationModel configurationModel) {
        this.configurationModel = configurationModel;
    }

    public void run() {
        app = Javalin
                .create(config -> config.addSinglePageRoot("/", "static/index.html", Location.CLASSPATH))
                .start(7000);

        app.get("/api/configuration", this::getConfiguration);

        initShutdownHooks();
    }

    private void getConfiguration(Context ctx) {
        ctx.json(this.configurationModel);
    }

    private void initShutdownHooks() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> app.stop()));

        app.events(event -> {
            event.serverStopping(() -> {
                logger.info("Server stopping");
            });
            event.serverStopped(() -> {
                logger.info("Server stopped");
            });
        });
    }

}
