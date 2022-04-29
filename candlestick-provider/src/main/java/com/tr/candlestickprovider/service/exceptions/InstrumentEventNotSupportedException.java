package com.tr.candlestickprovider.service.exceptions;

public class InstrumentEventNotSupportedException extends RuntimeException {
    public InstrumentEventNotSupportedException(String message) {
        super(String.format("QuoteEvent has undefined or faulty fields: %s", message));
    }
}
