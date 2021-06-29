package hr.fer.zvne.scada.simulator.models.messages;

import hr.fer.zvne.scada.simulator.models.Data;

import java.util.Arrays;

public class ChangeDataRequestBody {
    private String id;
    private Data[] data;

    public ChangeDataRequestBody() {}

    public ChangeDataRequestBody(String id, Data[] data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ChangeDataRequestBody{" +
                "id='" + id + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
