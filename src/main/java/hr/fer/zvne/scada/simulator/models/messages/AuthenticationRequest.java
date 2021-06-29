package hr.fer.zvne.scada.simulator.models.messages;

import hr.fer.zvne.scada.simulator.models.Message;

public class AuthenticationRequest extends Message<AuthenticationRequestBody> {

    public AuthenticationRequest() {}

    public AuthenticationRequest(String type, AuthenticationRequestBody body) {
        super(type, body);
    }
}
