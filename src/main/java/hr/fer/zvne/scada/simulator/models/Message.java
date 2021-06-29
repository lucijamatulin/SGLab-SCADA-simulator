package hr.fer.zvne.scada.simulator.models;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class Message<T> {
    private String type;
    private T body;

    public Message() {
    }

    public <T> Message(T readValue) {
    }

    public Message(String type, ObjectNode body) {
    }

    public Message(String type, T body) {
        this.type = type;
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", body=" + body +
                '}';
    }
}
