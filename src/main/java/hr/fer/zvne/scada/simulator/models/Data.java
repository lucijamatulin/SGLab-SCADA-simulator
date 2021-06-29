package hr.fer.zvne.scada.simulator.models;

import java.sql.Timestamp;

public class Data {
    private String key;
    private Object value;
    private String quality;
    private Timestamp timestamp;
    private String type;
    private String source;

    public Data() {}

    public Data(String key, Object value, String quality, Timestamp timestamp, String type, String source) {
        this.key = key;
        this.value = value;
        this.quality = quality;
        this.timestamp = timestamp;
        this.type = type;
        this.source = source;
    }

    public Data(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Data{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", quality='" + quality + '\'' +
                ", timestamp=" + timestamp +
                ", type='" + type + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
