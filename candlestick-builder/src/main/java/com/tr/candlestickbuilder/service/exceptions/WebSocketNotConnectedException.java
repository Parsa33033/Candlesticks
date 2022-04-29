package com.tr.candlestickbuilder.service.exceptions;

public class WebSocketNotConnectedException extends Throwable{

    public WebSocketNotConnectedException(String url) {
        super(String.format("Websocket not connected to %s, trying to reconnect...", url));
    }
}
