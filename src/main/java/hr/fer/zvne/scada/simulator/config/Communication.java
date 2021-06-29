package hr.fer.zvne.scada.simulator.config;

public class Communication {
    private int port;
    private int messageLength;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMessageLength() {
        return messageLength;
    }

    public void setMessageLength(int messageLength) {
        this.messageLength = messageLength;
    }

    @Override
    public String toString() {
        return "\n\tport=" + port +
                ", \n\tmessageLength=" + messageLength;
    }
}
