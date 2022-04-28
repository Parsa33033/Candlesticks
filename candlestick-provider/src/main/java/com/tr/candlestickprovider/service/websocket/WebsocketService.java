package com.tr.candlestickprovider.service.websocket;

import com.tr.candlestickprovider.config.CandlestickConfig;
import com.tr.candlestickprovider.consts.URL;
import com.tr.candlestickprovider.service.exceptions.WebSocketNotConnectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

@Service
public class WebsocketService {

    private final String url;

    public WebsocketService (CandlestickConfig candlestickConfig) {
        url = candlestickConfig.getPartner().getUrl();
    }

    public void connect(String endpoint, WebSocket.Listener listener) throws WebSocketNotConnectedException {
        try {
            CompletableFuture<WebSocket> webSocket = HttpClient
                    .newHttpClient()
                    .newWebSocketBuilder()
                    .buildAsync(URI.create(url + endpoint), listener);
            webSocket.get();
        } catch (Exception e) {
            throw new WebSocketNotConnectedException(url + endpoint);
        }
    }
}
