package com.tr.candlestickprovider.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickprovider.config.CandlestickConfig;
import com.tr.candlestickprovider.consts.Constant;
import com.tr.candlestickprovider.consts.URL;
import com.tr.candlestickprovider.model.dto.InstrumentEventDTO;
import com.tr.candlestickprovider.service.exceptions.InstrumentEventNotSupportedException;
import com.tr.candlestickprovider.service.exceptions.PartnerEventReceiveException;
import com.tr.candlestickprovider.service.exceptions.PartnerEventSendToQueueException;
import com.tr.candlestickprovider.service.exceptions.WebSocketNotConnectedException;
import com.tr.candlestickprovider.service.message.InstrumentSenderService;
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
 * Initiating the Websocket Client to read Instrument data
 */
@Service
public class InstrumentWebSocketClient implements WebSocket.Listener{

    Logger logger = LoggerFactory.getLogger(InstrumentWebSocketClient.class);

    private final ObjectMapper objectMapper;

    private final InstrumentSenderService instrumentSenderService;

    private final WebsocketService websocketService;

    private final String url;

    public InstrumentWebSocketClient(ObjectMapper objectMapper,
                                     InstrumentSenderService instrumentSenderService,
                                     WebsocketService websocketService,
                                     CandlestickConfig candlestickConfig) {
        this.objectMapper = objectMapper;
        this.instrumentSenderService = instrumentSenderService;
        this.websocketService = websocketService;
        this.url = candlestickConfig.getPartner().getUrl();
    }

    /**
     * Websocket client listening to Instrument events coming from the Partner Service
     */
    @PostConstruct
    public void getInstruments() {
        try {
            websocketService.connect(url, URL.INSTRUMENTS_ENDPOINT, this);
        } catch (WebSocketNotConnectedException e) {
            logger.info(e.getMessage());
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    getInstruments();
                }
            }, 5000);
        }

    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        try {
            InstrumentEventDTO instrumentEventDTO =
                    objectMapper.readValue(data.toString(), InstrumentEventDTO.class);
            logger.info("Received Instrument: ===> {}", instrumentEventDTO);
            instrumentSenderService.send(instrumentEventDTO);
        } catch (PartnerEventSendToQueueException e) {
            throw e;
        } catch (InstrumentEventNotSupportedException e) {
            throw e;
        } catch (Exception e) {
            throw new PartnerEventReceiveException(data.toString(), Constant.QUOTE, e.getMessage());
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
