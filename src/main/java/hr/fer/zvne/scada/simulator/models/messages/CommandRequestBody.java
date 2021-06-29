package hr.fer.zvne.scada.simulator.models.messages;

import hr.fer.zvne.scada.simulator.models.Command;

import java.util.Arrays;

public class CommandRequestBody {
    private String id;
    private Command[] data;

    public CommandRequestBody() {}

    public CommandRequestBody(String id, Command[] data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Command[] getData() {
        return data;
    }

    public void setData(Command[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommandRequestBody{" +
                "id='" + id + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
