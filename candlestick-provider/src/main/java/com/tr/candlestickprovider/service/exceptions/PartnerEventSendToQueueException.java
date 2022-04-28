package com.tr.candlestickprovider.service.exceptions;

public class PartnerEventSendToQueueException extends RuntimeException {

    public PartnerEventSendToQueueException(String message, String sender, String error) {
        super(String.format("The Rabbit template to send to %s queue failed to send message: %s to queue with error: %s",
                sender, message, error));
    }
}
