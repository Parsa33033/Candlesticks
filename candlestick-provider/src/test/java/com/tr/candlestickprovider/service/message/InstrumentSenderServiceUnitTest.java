package com.tr.candlestickprovider.service.message;

import com.tr.candlestickprovider.config.RabbitMockProvider;
import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.dto.InstrumentEventDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.service.exceptions.InstrumentEvenNotSupportedException;
import com.tr.candlestickprovider.service.exceptions.PartnerEventSendToQueueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {InstrumentSenderService.class, RabbitMockProvider.class})
class InstrumentSenderServiceUnitTest {

    private Type type = Type.ADD;

    private String description = "description";

    private String isin = "isin";

    private String timestamp;

    InstrumentEventDTO instrumentEventDTO;

    @Autowired
    InstrumentSenderService instrumentSenderService;

    @BeforeEach
    public void setUp() {
        timestamp = Instant.now().toString();
        instrumentEventDTO = new InstrumentEventDTO();
        InstrumentDTO instrumentDTO = new InstrumentDTO();
        instrumentDTO.setTimestamp(timestamp);
        instrumentDTO.setDescription(description);
        instrumentEventDTO.setInstrumentDTO(instrumentDTO);
        instrumentEventDTO.setType(type);
    }


    @Test
    public void testIfMessageIsSent() {
        assertDoesNotThrow(() -> instrumentSenderService.send(instrumentEventDTO));
    }

    public void testIfIsinIsNotCorrect() {
        instrumentEventDTO.getInstrumentDTO().setIsin(null);
        assertThrows(InstrumentEvenNotSupportedException.class, () -> instrumentSenderService.send(instrumentEventDTO));
        instrumentEventDTO.getInstrumentDTO().setIsin("");
        assertThrows(InstrumentEvenNotSupportedException.class, () -> instrumentSenderService.send(instrumentEventDTO));
    }

    public void testIfTypeIsNotCorrect() {
//        instrumentEventDTO.getInstrumentDTO().(null);
        assertThrows(InstrumentEvenNotSupportedException.class, () -> instrumentSenderService.send(instrumentEventDTO));
        instrumentEventDTO.getInstrumentDTO().setIsin("");
        assertThrows(InstrumentEvenNotSupportedException.class, () -> instrumentSenderService.send(instrumentEventDTO));
    }
}