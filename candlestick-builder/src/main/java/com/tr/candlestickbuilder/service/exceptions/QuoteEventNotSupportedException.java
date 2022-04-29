package com.tr.candlestickbuilder.service.exceptions;

public class QuoteEventNotSupportedException extends RuntimeException {
    public QuoteEventNotSupportedException(String message) {
        super(String.format("QuoteEvent has undefined or faulty fields: %s", message));
    }
}
