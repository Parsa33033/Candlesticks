package com.tr.candlestickprovider.service.websocket;

import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.tr.candlestickprovider.consts.URL;
import com.tr.candlestickprovider.service.exceptions.WebSocketNotConnectedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebsocketService.class})
class WebsocketServiceUnitTest {
    Logger logger = LoggerFactory.getLogger(WebsocketServiceUnitTest.class);

    static String url = "ws://localhost:28765";

    @Autowired
    WebsocketService websocketService;

    @BeforeEach
    void setUp() throws IOException, WebSocketException {
        new WebSocketFactory().createSocket(url).connect().sendText("asodifj");

    }

    @Test
    void connect() throws WebSocketNotConnectedException {
        websocketService.connect(url, URL.INSTRUMENTS_ENDPOINT, new WebSocket.Listener() {
            @Override
            public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                logger.info("-----> {}", data);
                return WebSocket.Listener.super.onText(webSocket, data, last);
            }
        });

    }

    @AfterEach
    void tearDown() {


    }
}