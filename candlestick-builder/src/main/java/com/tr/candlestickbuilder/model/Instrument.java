package com.tr.candlestickbuilder.model;

import com.tr.candlestickbuilder.model.enums.Type;

public class Instrument {

    private String description;

    private Type type;

    private String timestamp;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "description='" + description + '\'' +
                ", type=" + type +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
