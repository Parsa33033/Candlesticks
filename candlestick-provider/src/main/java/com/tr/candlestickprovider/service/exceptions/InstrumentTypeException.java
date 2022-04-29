package com.tr.candlestickprovider.service.exceptions;

import com.tr.candlestickprovider.model.enums.Type;

public class InstrumentTypeException extends Exception{
    public InstrumentTypeException(Type type) {
        super(String.format("Type for instrument is not correct!", type));
    }
}
