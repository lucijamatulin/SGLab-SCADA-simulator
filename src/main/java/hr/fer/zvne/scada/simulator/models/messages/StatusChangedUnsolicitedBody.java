package hr.fer.zvne.scada.simulator.models.messages;

import hr.fer.zvne.scada.simulator.models.Data;

public class StatusChangedUnsolicitedBody {
    private String status;
    private Data data;

    public StatusChangedUnsolicitedBody() {}

    public StatusChangedUnsolicitedBody(String status, Data data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StatusChangedUnsolicitedBody{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
