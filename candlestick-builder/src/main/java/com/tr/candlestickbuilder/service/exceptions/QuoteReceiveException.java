package com.tr.candlestickbuilder.service.exceptions;

public class QuoteReceiveException extends RuntimeException {

    public QuoteReceiveException(String quote, String message) {
        super(String.format("Quote Received faced exception with message: %s ,for instrument: %s", message, quote));
    }
}
