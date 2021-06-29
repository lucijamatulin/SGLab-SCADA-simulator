package hr.fer.zvne.scada.simulator.models.messages;

import hr.fer.zvne.scada.simulator.models.Data;

import java.util.Arrays;

public class AuthenticationResponseBody {
    private Boolean success;
    private String status;
    private Data[] data;

    public AuthenticationResponseBody() {}

    public AuthenticationResponseBody(Boolean success, String status, Data[] data) {
        this.success = success;
        this.status = status;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AuthenticationResponseBody{" +
                "success=" + success +
                ", status='" + status + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
