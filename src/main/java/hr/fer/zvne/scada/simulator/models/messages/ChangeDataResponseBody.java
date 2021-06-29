package hr.fer.zvne.scada.simulator.models.messages;

import java.util.Arrays;

public class ChangeDataResponseBody {
    private String id;
    private Boolean[] success;

    public ChangeDataResponseBody(String id, Boolean[] success) {
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
        return "ChangeDataResponseBody{" +
                "id='" + id + '\'' +
                ", success=" + Arrays.toString(success) +
                '}';
    }
}
