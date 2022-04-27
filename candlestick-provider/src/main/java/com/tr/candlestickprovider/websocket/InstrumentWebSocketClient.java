package com.tr.candlestickprovider.websocket;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickprovider.consts.URL;
import com.tr.candlestickprovider.model.dto.InstrumentEventDTO;
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
import java.util.concurrent.CompletionStage;


/**
 * Initiating the Websocket Client to read Instrument data
 */
@Service
public class InstrumentWebSocketClient implements WebSocket.Listener{

    Logger logger = LoggerFactory.getLogger(InstrumentWebSocketClient.class);

    private final ObjectMapper objectMapper;
    private final InstrumentSenderService instrumentSenderService;

    @Value("${application.partner-url}")
    private String url;


    public InstrumentWebSocketClient(ObjectMapper objectMapper,
                                     InstrumentSenderService instrumentSenderService) {
        this.objectMapper = objectMapper;
        this.instrumentSenderService = instrumentSenderService;
    }

    /**
     * Websocket client listening to Instrument events coming from the Partner Service
     */
    @PostConstruct
    public void getInstruments() {
        HttpClient
                .newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(URI.create(url + URL.INSTRUMENTS_ENDPOINT), this);

    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        try {
            InstrumentEventDTO instrumentEventDTO =
                    objectMapper.readValue(data.toString(), InstrumentEventDTO.class);
            logger.info("Instrument: ===> {}", instrumentEventDTO);
            instrumentSenderService.send(instrumentEventDTO);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        getInstruments();
        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        logger.error("Error (Websocket) on {} with message: {}", URL.INSTRUMENTS_ENDPOINT, error.getMessage());
        getInstruments();
        WebSocket.Listener.super.onError(webSocket, error);
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        logger.error("Opened {} Websocket endpoint with protocol: {}", URL.INSTRUMENTS_ENDPOINT, webSocket.getSubprotocol());
        WebSocket.Listener.super.onOpen(webSocket);
    }
}
