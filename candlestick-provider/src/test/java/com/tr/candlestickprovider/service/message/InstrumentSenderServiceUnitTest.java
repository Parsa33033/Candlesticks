package com.tr.candlestickprovider.service.message;

import com.tr.candlestickprovider.config.RabbitConfig;
import com.tr.candlestickprovider.config.RabbitMockProvider;
import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.dto.InstrumentEventDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.service.exceptions.InstrumentEventNotSupportedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        InstrumentSenderService.class,
        RabbitMockProvider.class
})
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
        instrumentDTO.setType(type);
        instrumentDTO.setIsin(isin);
        instrumentEventDTO.setInstrumentDTO(instrumentDTO);
        instrumentEventDTO.setType(type);

    }

    @Test
    public void testIfMessageIsSent() {
        assertDoesNotThrow(() -> instrumentSenderService.send(instrumentEventDTO));
    }

    @Test
    public void testIfIsinIsNotCorrect() {
        instrumentEventDTO.getInstrumentDTO().setIsin(null);
        assertThrows(InstrumentEventNotSupportedException.class, () -> instrumentSenderService.send(instrumentEventDTO));
        instrumentEventDTO.getInstrumentDTO().setIsin("");
        assertThrows(InstrumentEventNotSupportedException.class, () -> instrumentSenderService.send(instrumentEventDTO));
        instrumentEventDTO.getInstrumentDTO().setIsin(isin);
    }

    @Test
    public void testIfTypeIsNotCorrect() {
        instrumentEventDTO.setType(null);
        instrumentEventDTO.getInstrumentDTO().setType(null);
        assertThrows(InstrumentEventNotSupportedException.class, () -> instrumentSenderService.send(instrumentEventDTO));
        instrumentEventDTO.getInstrumentDTO().setType(Type.QUOTE);
        instrumentEventDTO.setType(Type.QUOTE);
        assertThrows(InstrumentEventNotSupportedException.class, () -> instrumentSenderService.send(instrumentEventDTO));
        instrumentEventDTO.getInstrumentDTO().setType(type);
    }

    @Test
    public void testIsNotCorrect() {

        boolean result = instrumentSenderService.isNotCorrect(instrumentEventDTO);
        assertEquals(result, false);

        //check isin
        instrumentEventDTO.getInstrumentDTO().setIsin(null);
        result = instrumentSenderService.isNotCorrect(instrumentEventDTO);
        assertEquals(result, true);
        instrumentEventDTO.getInstrumentDTO().setIsin("");
        result = instrumentSenderService.isNotCorrect(instrumentEventDTO);
        assertEquals(result, true);
        instrumentEventDTO.getInstrumentDTO().setIsin(isin);

        //check type
        instrumentEventDTO.setType(null);
        result = instrumentSenderService.isNotCorrect(instrumentEventDTO);
        assertEquals(result, true);
        instrumentEventDTO.setType(Type.QUOTE);
        result = instrumentSenderService.isNotCorrect(instrumentEventDTO);
        assertEquals(result, true);
        instrumentEventDTO.setType(type);

        //check timestmap
        instrumentEventDTO.getInstrumentDTO().setTimestamp("");
        result = instrumentSenderService.isNotCorrect(instrumentEventDTO);
        assertEquals(result, true);
        instrumentEventDTO.getInstrumentDTO().setTimestamp(null);
        result = instrumentSenderService.isNotCorrect(instrumentEventDTO);
        assertEquals(result, true);
        instrumentEventDTO.getInstrumentDTO().setTimestamp(timestamp);


        //check description
        instrumentEventDTO.getInstrumentDTO().setDescription(null);
        result = instrumentSenderService.isNotCorrect(instrumentEventDTO);
        assertEquals(result, true);
        instrumentEventDTO.getInstrumentDTO().setDescription("");
        result = instrumentSenderService.isNotCorrect(instrumentEventDTO);
        assertEquals(result, true);
        instrumentEventDTO.getInstrumentDTO().setDescription(description);

        //check type of instrumentDTO
        instrumentEventDTO.getInstrumentDTO().setType(null);
        result = instrumentSenderService.isNotCorrect(instrumentEventDTO);
        assertEquals(result, true);
        instrumentEventDTO.getInstrumentDTO().setType(Type.QUOTE);
        result = instrumentSenderService.isNotCorrect(instrumentEventDTO);
        assertEquals(result, true);
        instrumentEventDTO.getInstrumentDTO().setType(Type.ADD);

        result = instrumentSenderService.isNotCorrect(instrumentEventDTO);
        assertEquals(result, false);
    }
}