package com.tr.candlestickbuilder.service.exceptions;

public class QuoteNotHandledWhenReceivedException extends RuntimeException {

    public QuoteNotHandledWhenReceivedException(String quote, String message) {
        super(String.format("Quote %s not handled correctly when received with message: %s", quote, message));
    }
}
