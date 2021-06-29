package hr.fer.zvne.scada.simulator.models.messages;

import hr.fer.zvne.scada.simulator.models.Message;

public class CommandRequest extends Message<CommandRequestBody> {

    public CommandRequest() {}

    public CommandRequest(String type, CommandRequestBody body) {
        super(type, body);
    }

}
