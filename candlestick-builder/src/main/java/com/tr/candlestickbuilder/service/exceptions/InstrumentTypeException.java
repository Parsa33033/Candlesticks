package com.tr.candlestickbuilder.service.exceptions;

import com.tr.candlestickbuilder.model.enums.Type;

public class InstrumentTypeException extends Exception{
    public InstrumentTypeException(Type type) {
        super(String.format("Type for instrument is not correct!", type));
    }
}
