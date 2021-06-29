package hr.fer.zvne.scada.simulator.models.messages;

import hr.fer.zvne.scada.simulator.models.Message;

public class ChangeDataRequest extends Message<ChangeDataRequestBody> {

    public ChangeDataRequest() {}

    public ChangeDataRequest(String type, ChangeDataRequestBody body) {
        super(type, body);
    }
}
