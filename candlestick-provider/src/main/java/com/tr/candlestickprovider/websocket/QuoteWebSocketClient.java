package com.tr.candlestickprovider.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickprovider.config.CandlestickConfig;
import com.tr.candlestickprovider.consts.Constant;
import com.tr.candlestickprovider.consts.URL;
import com.tr.candlestickprovider.model.dto.QuoteEventDTO;
import com.tr.candlestickprovider.service.exceptions.*;
import com.tr.candlestickprovider.service.message.InstrumentSenderService;
import com.tr.candlestickprovider.service.message.QuoteSenderService;
import com.tr.candlestickprovider.service.websocket.WebsocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.http.WebSocket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletionStage;

/**
 * Initiating the Websocket Client to read Quote data
 */
@Service
public class QuoteWebSocketClient implements WebSocket.Listener {
    Logger logger = LoggerFactory.getLogger(InstrumentWebSocketClient.class);

    private final ObjectMapper objectMapper;

    private final InstrumentSenderService instrumentSenderService;

    private final QuoteSenderService quoteSenderService;

    private final WebsocketService websocketService;

    private String url;

    public QuoteWebSocketClient(ObjectMapper objectMapper,
                                InstrumentSenderService instrumentSenderService,
                                QuoteSenderService quoteSenderService,
                                WebsocketService websocketService,
                                CandlestickConfig candlestickConfig) {
        this.objectMapper = objectMapper;
        this.instrumentSenderService = instrumentSenderService;
        this.quoteSenderService = quoteSenderService;
        this.websocketService = websocketService;
        this.url = candlestickConfig.getPartner().getUrl();
    }


    /**
     * Websocket client listening to Quote events coming from the Partner Service
     */
    @PostConstruct
    public void getQuotes() {
        try {
            websocketService.connect(url, URL.QUOTES_ENDPOINT, this);
        } catch (WebSocketNotConnectedException e) {
            logger.info(e.getMessage());
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    getQuotes();
                }
            }, 5000);
        }
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        try {
            QuoteEventDTO quoteEventDTO =
                    objectMapper.readValue(data.toString(), QuoteEventDTO.class);
            logger.info("Received Quote: ===> {}", quoteEventDTO);
            quoteSenderService.send(quoteEventDTO);
        } catch (PartnerEventSendToQueueException e) {
            throw e;
        } catch (QuoteEventNotSupportedException e) {
            throw e;
        } catch (Exception e) {
            throw new PartnerEventReceiveException(data.toString(), Constant.QUOTE, e.getMessage());
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
        getQuotes();
        WebSocket.Listener.super.onError(webSocket, error);
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        logger.error("Opened {} Websocket endpoint with protocol: {}", URL.QUOTES_ENDPOINT, webSocket.getSubprotocol());
        WebSocket.Listener.super.onOpen(webSocket);
    }
}
