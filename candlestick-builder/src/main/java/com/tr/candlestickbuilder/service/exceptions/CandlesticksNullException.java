package com.tr.candlestickbuilder.service.exceptions;

public class CandlesticksNullException extends RuntimeException {
    public CandlesticksNullException() {
        super("Candlestick was null when fetched");
    }
}
