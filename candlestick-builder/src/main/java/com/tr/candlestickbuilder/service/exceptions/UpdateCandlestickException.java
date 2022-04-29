package com.tr.candlestickbuilder.service.exceptions;

public class UpdateCandlestickException extends RuntimeException{

    public UpdateCandlestickException(String isin, String message) {
        super(String.format("Exception on updating the candlestick with isin %s and message: %s", isin, message));
    }
}
