package hr.fer.zvne.scada.simulator.models.messages;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hr.fer.zvne.scada.simulator.models.Message;

import java.util.ArrayList;

public class DataChangedUnsolicited extends Message<DataChangedUnsolicitedBody> {

    public DataChangedUnsolicited() {}

    public DataChangedUnsolicited(String type, DataChangedUnsolicitedBody body) {
        super(type, body);
    }

    public ObjectNode toObjectNode() {
        ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());
        ObjectNode response = objectMapper.createObjectNode();
        ObjectNode responseBody = objectMapper.createObjectNode();
        ArrayList<ObjectNode> responseData = new ArrayList<>();

        response.put("type", getType());

        for(int i = 0; i < getBody().getData().length; i++) {
            ObjectNode fieldName = objectMapper.createObjectNode();

            fieldName.put("key", getBody().getData()[i].getKey());
            fieldName.put("value", String.valueOf(getBody().getData()[i].getValue()));
            fieldName.put("quality", getBody().getData()[i].getQuality());
            fieldName.put("timestamp", String.valueOf(getBody().getData()[i].getTimestamp()));
            fieldName.put("type", getBody().getData()[i].getType());
            fieldName.put("source", getBody().getData()[i].getSource());

            responseData.add(fieldName);
        }

        responseBody.putArray("data").addAll(responseData);
        response.set("body", responseBody);
        return response;
    }
}
