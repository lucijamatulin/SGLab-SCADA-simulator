package hr.fer.zvne.scada.simulator.models.messages;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hr.fer.zvne.scada.simulator.models.Message;

public class ChangeDataResponse  extends Message<ChangeDataResponseBody> {

    public ChangeDataResponse() {}

    public ChangeDataResponse(String type, ChangeDataResponseBody body) {
        super(type, body);
    }

    public ObjectNode toObjectNode() {
        ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());
        ObjectNode response = objectMapper.createObjectNode();
        ObjectNode responseBody = objectMapper.createObjectNode();
        ArrayNode fieldName = objectMapper.createArrayNode();

        response.put("type", getType());

        responseBody.put("id", getBody().getId());
        for(int i = 0; i < getBody().getSuccess().length; i++) {
            fieldName.add(getBody().getSuccess()[i]);
        }

        responseBody.putArray("success").addAll(fieldName);
        response.set("body", responseBody);
        return response;
    }
}
