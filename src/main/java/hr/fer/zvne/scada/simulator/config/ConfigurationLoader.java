package hr.fer.zvne.scada.simulator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.*;


public class ConfigurationLoader {
    private final ObjectMapper objectMapper;
    private final String CONFIG_KEY = "configuration.properties";
    private final String INTERNAL_CONFIGURATION_FILE = "configuration.yml";
    private InputStream input = null;

    public ConfigurationLoader() {
        this.objectMapper = new ObjectMapper(new YAMLFactory());
    }


    public <T> T loadConfiguration(Class<T> cls) throws Exception {
        System.out.println("\n\nConfiguration loading started.");
        String loggingConfigurationLocation = System.getProperty(CONFIG_KEY);
        if (loggingConfigurationLocation != null) {
            try {
                return this.objectMapper.readValue(new File(loggingConfigurationLocation), cls);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        else {
            try {
                input = getClass().getClassLoader().getResourceAsStream(INTERNAL_CONFIGURATION_FILE);
                return this.objectMapper.readValue(input, cls);
            } catch (FileNotFoundException e) {
                throw new UncheckedIOException(e);
            } finally {
                if (input != null) {
                    input.close();
                }
            }
        }
    }
}