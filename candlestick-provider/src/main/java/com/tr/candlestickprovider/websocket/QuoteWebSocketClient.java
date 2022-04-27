package com.tr.candlestickprovider.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickprovider.consts.URL;
import com.tr.candlestickprovider.model.dto.QuoteEventDTO;
import com.tr.candlestickprovider.service.message.InstrumentSenderService;
import com.tr.candlestickprovider.service.message.QuoteSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.Instant;
import java.util.concurrent.CompletionStage;

/**
 * Initiating the Websocket Client to read Quote data
 */
@Service
public class QuoteWebSocketClient implements WebSocket.Listener{
    Logger logger = LoggerFactory.getLogger(InstrumentWebSocketClient.class);

    private final ObjectMapper objectMapper;
    private final InstrumentSenderService instrumentSenderService;
    private final QuoteSenderService quoteSenderService;

    @Value("${application.partner-url}")
    private String url;


    public QuoteWebSocketClient(ObjectMapper objectMapper,
                           InstrumentSenderService instrumentSenderService,
                           QuoteSenderService quoteSenderService) {
        this.objectMapper = objectMapper;
        this.instrumentSenderService = instrumentSenderService;
        this.quoteSenderService = quoteSenderService;
    }


    /**
     * Websocket client listening to Quote events coming from the Partner Service
     */
    @PostConstruct
    public void getQuotes() {
        HttpClient
                .newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(URI.create(url + URL.QUOTES_ENDPOINT), this);

    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        try {
            QuoteEventDTO quoteEventDTO =
                    objectMapper.readValue(data.toString(), QuoteEventDTO.class);
            quoteSenderService.send(quoteEventDTO);
        } catch (JsonProcessingException e) {
            logger.warn(e.getMessage());
        }
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        getQuotes();
        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        logger.error("Error (Websocket) on {} with message: {}", URL.QUOTES_ENDPOINT, error.getMessage());
        WebSocket.Listener.super.onError(webSocket, error);
        getQuotes();
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        logger.error("Opened {} Websocket endpoint with protocol: {}", URL.QUOTES_ENDPOINT, webSocket.getSubprotocol());
        WebSocket.Listener.super.onOpen(webSocket);
    }
}
