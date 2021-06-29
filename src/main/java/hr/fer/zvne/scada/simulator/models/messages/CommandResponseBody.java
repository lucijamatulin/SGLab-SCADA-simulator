package hr.fer.zvne.scada.simulator.models.messages;

public class CommandResponseBody {
    private String id;
    private Boolean[] success;

    public CommandResponseBody() {}

    public CommandResponseBody(String id, Boolean[] success) {
        this.id = id;
        this.success = success;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean[] getSuccess() {
        return success;
    }

    public void setSuccess(Boolean[] success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "CommandResponseBody{" +
                "id='" + id + '\'' +
                ", success=" + success +
                '}';
    }
}
