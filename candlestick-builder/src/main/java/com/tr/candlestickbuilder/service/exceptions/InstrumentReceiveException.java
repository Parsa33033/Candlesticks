package com.tr.candlestickbuilder.service.exceptions;

public class InstrumentReceiveException extends RuntimeException {

    public InstrumentReceiveException(String instrument, String message) {
        super(String.format("Instrument Received faced exception with message: %s ,for instrument: %s", message, instrument));
    }
}
