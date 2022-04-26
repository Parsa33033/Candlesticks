package com.tr.candlestickbuilder.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tr.candlestickbuilder.model.enums.Type;
import com.tr.candlestickbuilder.model.redis.Candlestick;

import java.util.List;

public class InstrumentDTO {

    private String isin;

    private String description;

    private Type type;

    private List<CandlestickDTO> candlesticks;

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

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

    public List<CandlestickDTO> getCandlesticks() {
        return candlesticks;
    }

    public void setCandlesticks(List<CandlestickDTO> candlesticks) {
        this.candlesticks = candlesticks;
    }

    @Override
    public String toString() {
        return "InstrumentDTO{" +
                "isin='" + isin + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", candlesticks=" + candlesticks +
                '}';
    }
}
