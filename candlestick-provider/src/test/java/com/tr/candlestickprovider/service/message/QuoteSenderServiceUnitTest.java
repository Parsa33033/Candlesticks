package com.tr.candlestickprovider.service.message;

import com.tr.candlestickprovider.config.RabbitConfig;
import com.tr.candlestickprovider.config.RabbitMockProvider;
import com.tr.candlestickprovider.model.dto.QuoteDTO;
import com.tr.candlestickprovider.model.dto.QuoteEventDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.service.exceptions.QuoteEventNotSupportedException;
import com.tr.candlestickprovider.service.exceptions.QuoteEventNotSupportedException;
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
        QuoteSenderService.class,
        RabbitMockProvider.class
})
class QuoteSenderServiceUnitTest {
    private Type type = Type.QUOTE;

    private double price = 1d;

    private String isin = "isin";

    private String timestamp;

    QuoteEventDTO quoteEventDTO;

    @Autowired
    QuoteSenderService quoteSenderService;

    @BeforeEach
    public void setUp() {
        timestamp = Instant.now().toString();
        quoteEventDTO = new QuoteEventDTO();
        quoteEventDTO.setType(type);
        QuoteDTO quoteDTO = new QuoteDTO();
        quoteDTO.setTimestamp(timestamp);
        quoteDTO.setPrice(price);
        quoteDTO.setIsin(isin);
        quoteEventDTO.setQuoteDTO(quoteDTO);
    }

    @Test
    public void testIfMessageIsSent() {
        assertDoesNotThrow(() -> quoteSenderService.send(quoteEventDTO));
    }

    @Test
    public void testIfIsinIsNotCorrect() {
        quoteEventDTO.getQuoteDTO().setIsin(null);
        assertThrows(QuoteEventNotSupportedException.class, () -> quoteSenderService.send(quoteEventDTO));
        quoteEventDTO.getQuoteDTO().setIsin("");
        assertThrows(QuoteEventNotSupportedException.class, () -> quoteSenderService.send(quoteEventDTO));
        quoteEventDTO.getQuoteDTO().setIsin(isin);
    }

    @Test
    public void testIfPriceIsNotCorrect() {
        quoteEventDTO.getQuoteDTO().setPrice(-1);
        assertThrows(QuoteEventNotSupportedException.class, () -> quoteSenderService.send(quoteEventDTO));
        quoteEventDTO.getQuoteDTO().setPrice(price);
    }

    @Test
    public void testIsNotCorrect() {
        QuoteDTO quoteDTO = quoteEventDTO.getQuoteDTO();
        boolean result = quoteSenderService.isNotCorrect(quoteDTO);
        assertEquals(result, false);

        //check isin
        quoteDTO.setIsin(null);
        result = quoteSenderService.isNotCorrect(quoteDTO);
        assertEquals(result, true);
        quoteDTO.setIsin("");
        result = quoteSenderService.isNotCorrect(quoteDTO);
        assertEquals(result, true);
        quoteDTO.setIsin(isin);

        //check price
        quoteDTO.setPrice(-1);
        result = quoteSenderService.isNotCorrect(quoteDTO);
        assertEquals(result, true);
        quoteDTO.setPrice(price);

        //check timestamp
        quoteDTO.setTimestamp(null);
        result = quoteSenderService.isNotCorrect(quoteDTO);
        assertEquals(result, true);
        quoteDTO.setTimestamp("");
        result = quoteSenderService.isNotCorrect(quoteDTO);
        assertEquals(result, true);
        quoteDTO.setTimestamp(timestamp);
    }
}