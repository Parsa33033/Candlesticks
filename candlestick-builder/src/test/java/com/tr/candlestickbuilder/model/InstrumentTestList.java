package com.tr.candlestickbuilder.model;

import java.util.List;

public class InstrumentTestList {
    List<InstrumentTest> instrumentList;

    public List<InstrumentTest> getInstrumentList() {
        return instrumentList;
    }

    public void setInstrumentList(List<InstrumentTest> instrumentList) {
        this.instrumentList = instrumentList;
    }

    @Override
    public String toString() {
        return "InstrumentTestList{" +
                "instrumentList=" + instrumentList +
                '}';
    }
}
