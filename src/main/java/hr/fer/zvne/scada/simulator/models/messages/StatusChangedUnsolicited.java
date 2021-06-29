package hr.fer.zvne.scada.simulator.models.messages;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hr.fer.zvne.scada.simulator.models.Message;

public class StatusChangedUnsolicited extends Message<StatusChangedUnsolicitedBody> {

    public StatusChangedUnsolicited() {}

    public StatusChangedUnsolicited(String type, StatusChangedUnsolicitedBody body) {
        super(type, body);
    }

    public ObjectNode toObjectNode() {
        ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());
        ObjectNode response = objectMapper.createObjectNode();
        ObjectNode response_body = objectMapper.createObjectNode();

        response.put("type", getType());

        response_body.put("status", getBody().getStatus());
        response_body.put("data", String.valueOf(getBody().getData()));

        response.set("body", response_body);
        return response;
    }
}
