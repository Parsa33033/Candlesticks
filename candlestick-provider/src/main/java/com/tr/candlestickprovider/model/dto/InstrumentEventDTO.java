package com.tr.candlestickprovider.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tr.candlestickprovider.model.enums.Type;

public class InstrumentEventDTO {

    private Type type;

    @JsonProperty("data")
    private InstrumentDTO instrumentDTO;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public InstrumentDTO getInstrumentDTO() {
        return instrumentDTO;
    }

    public void setInstrumentDTO(InstrumentDTO instrumentDTO) {
        this.instrumentDTO = instrumentDTO;
    }

    @Override
    public String toString() {
        return "InstrumentEventDTO{" +
                "type=" + type +
                ", data=" + instrumentDTO +
                '}';
    }
}
