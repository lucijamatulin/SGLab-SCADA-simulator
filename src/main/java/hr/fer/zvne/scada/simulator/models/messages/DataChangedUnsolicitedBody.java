package hr.fer.zvne.scada.simulator.models.messages;

import hr.fer.zvne.scada.simulator.models.Data;

import java.util.Arrays;

public class DataChangedUnsolicitedBody {
    private Data[] data;

    public DataChangedUnsolicitedBody() {}

    public DataChangedUnsolicitedBody(Data[] data) {
        this.data = data;
    }

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataChangedUnsolicitedBody{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
