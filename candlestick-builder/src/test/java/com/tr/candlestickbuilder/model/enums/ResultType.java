package com.tr.candlestickbuilder.model.enums;

import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.dto.QuoteDTO;
import com.tr.candlestickbuilder.service.exceptions.InstrumentNotFoundException;
import com.tr.candlestickbuilder.service.exceptions.InstrumentReceiveException;
import com.tr.candlestickbuilder.service.exceptions.QuoteReceiveException;

public enum ResultType {
    INSTRUMENT_EXCEPTION (InstrumentReceiveException.class),
    QUOTE_EXCEPTION (QuoteReceiveException.class),
    INSTRUMENT_NOT_FOUND (InstrumentNotFoundException.class),
    OK (String.class);

    private Class result;

    ResultType(Class result) {
        this.result = result;
    }

    public Class getResult() {
        return result;
    }

    public void setResult(Class result) {
        this.result = result;
    }
}
