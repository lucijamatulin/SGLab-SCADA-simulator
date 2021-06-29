package hr.fer.zvne.scada.simulator.config;

public class CommandProperties {
    private String key;
    private String messageType;
    private String dataType;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "CommandProperties{" +
                "key='" + key + '\'' +
                ", messageType='" + messageType + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }
}
