package com.tr.candlestickprovider.service.exceptions;

public class InstrumentEvenNotSupportedException extends RuntimeException {
    public InstrumentEvenNotSupportedException(String message) {
        super(String.format("QuoteEvent has undefined or faulty fields: %s", message));
    }
}
