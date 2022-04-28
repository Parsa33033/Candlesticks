package com.tr.candlestickprovider.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickprovider.config.CandlestickConfig;
import com.tr.candlestickprovider.config.RabbitMockProvider;
import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.dto.InstrumentEventDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.service.exceptions.PartnerEventReceiveException;
import com.tr.candlestickprovider.service.exceptions.PartnerEventSendToQueueException;
import com.tr.candlestickprovider.service.message.InstrumentSenderService;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        InstrumentWebSocketClient.class,
        InstrumentSenderService.class,
        ObjectMapper.class,
        InstrumentSenderService.class,
        WebsocketService.class,
        CandlestickConfig.class,
        RabbitMockProvider.class
})
class InstrumentWebSocketClientIntegrationTest {

    private Type type = Type.ADD;

    private String description = "description";

    private String isin = "isin";

    private String timestamp;

    InstrumentEventDTO instrumentEventDTO;

    @Mock
    WebSocket websocket;

    @Autowired
    InstrumentWebSocketClient instrumentWebSocketClient;

    @BeforeEach
    void setUp() {
        timestamp = Instant.now().toString();
        instrumentEventDTO = new InstrumentEventDTO();
        InstrumentDTO instrumentDTO = new InstrumentDTO();
        instrumentDTO.setIsin(isin);
        instrumentDTO.setTimestamp(timestamp);
        instrumentDTO.setDescription(description);
        instrumentEventDTO.setInstrumentDTO(instrumentDTO);
        instrumentEventDTO.setType(type);
    }

    @Test
    void onText() throws JsonProcessingException {
        assertThrows(PartnerEventReceiveException.class,
                () -> instrumentWebSocketClient.onText(websocket, "", false));
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(instrumentEventDTO);
        assertDoesNotThrow(() -> instrumentWebSocketClient.onText(websocket, data, false));
    }
}