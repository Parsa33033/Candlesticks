package com.tr.candlestickbuilder.model;

import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.dto.InstrumentEventDTO;
import com.tr.candlestickbuilder.model.enums.ResultType;

public class InstrumentTest {
    InstrumentEventDTO instrumentEventDTO;

    ResultType result;

    public InstrumentEventDTO getInstrumentEventDTO() {
        return instrumentEventDTO;
    }

    public void setInstrumentEventDTO(InstrumentEventDTO instrumentEventDTO) {
        this.instrumentEventDTO = instrumentEventDTO;
    }

    public ResultType getResult() {
        return result;
    }

    public void setResult(ResultType result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "InstrumentTest{" +
                "instrumentEventDTO=" + instrumentEventDTO +
                ", result=" + result +
                '}';
    }
}
