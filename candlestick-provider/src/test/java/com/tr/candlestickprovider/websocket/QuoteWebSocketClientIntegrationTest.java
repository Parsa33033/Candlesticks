package com.tr.candlestickprovider.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickprovider.config.CandlestickConfig;
import com.tr.candlestickprovider.config.RabbitMockProvider;
import com.tr.candlestickprovider.model.dto.QuoteDTO;
import com.tr.candlestickprovider.model.dto.QuoteEventDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.service.exceptions.PartnerEventReceiveException;
import com.tr.candlestickprovider.service.exceptions.PartnerEventSendToQueueException;
import com.tr.candlestickprovider.service.message.InstrumentSenderService;
import com.tr.candlestickprovider.service.message.QuoteSenderService;
import com.tr.candlestickprovider.service.websocket.WebsocketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.http.WebSocket;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        QuoteWebSocketClient.class,
        QuoteSenderService.class,
        ObjectMapper.class,
        InstrumentSenderService.class,
        WebsocketService.class,
        CandlestickConfig.class,
        RabbitMockProvider.class
})
class QuoteWebSocketClientIntegrationTest {

    String isin = "isin";

    double price = 1d;

    Instant timestamp;

    QuoteEventDTO quoteEventDTO;

    @Mock
    WebSocket websocket;

    @Autowired
    QuoteWebSocketClient quoteWebSocketClient;

    @BeforeEach
    void setUp() {
        timestamp = Instant.now();
        quoteEventDTO = new QuoteEventDTO();
        QuoteDTO quoteDTO = new QuoteDTO();
        quoteDTO.setTimestamp(timestamp.toString());
        quoteDTO.setIsin(isin);
        quoteDTO.setPrice(price);
        quoteEventDTO.setQuoteDTO(quoteDTO);
        quoteEventDTO.setType(Type.QUOTE);
    }

    @Test
    void onText() throws JsonProcessingException {
        assertThrows(PartnerEventReceiveException.class,
                () -> quoteWebSocketClient.onText(websocket, "", false));
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(quoteEventDTO);
        assertThrows(PartnerEventSendToQueueException.class, () -> quoteWebSocketClient.onText(websocket, data, false));
    }
}