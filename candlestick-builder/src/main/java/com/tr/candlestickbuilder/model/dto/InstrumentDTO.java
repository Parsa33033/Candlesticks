package com.tr.candlestickbuilder.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tr.candlestickbuilder.model.enums.Type;

import java.util.List;

public class InstrumentDTO {

    private String isin;

    private String description;

    private Type type;

    private String timestamp;

    @JsonIgnore
    private List<CandlestickDTO> candlesticks;

    public InstrumentDTO() {
        super();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<CandlestickDTO> getCandlesticks() {
        return candlesticks;
    }

    public void setCandlesticks(List<CandlestickDTO> candlesticks) {
        this.candlesticks = candlesticks;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "InstrumentDTO{" +
                "isin='" + isin + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", timestamp='" + timestamp + '\'' +
                ", candlesticks=" + candlesticks +
                '}';
    }
}
