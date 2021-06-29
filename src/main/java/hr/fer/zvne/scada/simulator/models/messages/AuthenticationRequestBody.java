package hr.fer.zvne.scada.simulator.models.messages;

import java.util.Set;

public class AuthenticationRequestBody {
    private String username;
    private String password;
    private Set<String> subscriptions;

    public AuthenticationRequestBody() {}

    public AuthenticationRequestBody(String username, String password, Set<String> subscriptions) {
        this.username = username;
        this.password = password;
        this.subscriptions = subscriptions;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<String> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public String toString() {
        return "AuthenticationRequestBody{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", subscriptions=" + subscriptions +
                '}';
    }
}
