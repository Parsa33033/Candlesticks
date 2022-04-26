package com.tr.candlestickbuilder.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InstrumentNotFoundException extends RuntimeException{
    public InstrumentNotFoundException(String isin) {
        super(String.format("Instrument with id:{} does not exist!", isin));
    }
}
