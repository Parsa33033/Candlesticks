package com.tr.candlestickprovider.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickprovider.config.ApplicationConfig;
import com.tr.candlestickprovider.model.dto.CandlestickDTO;
import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.redis.InstrumentHash;
import com.tr.candlestickprovider.repository.InstrumentDocumentRepository;
import com.tr.candlestickprovider.repository.InstrumentHashRepository;
import com.tr.candlestickprovider.service.InstrumentService;
import com.tr.candlestickprovider.service.exceptions.InstrumentNotFoundException;
import com.tr.candlestickprovider.service.impl.InstrumentDocumentServiceImpl;
import com.tr.candlestickprovider.service.impl.InstrumentHashServiceImpl;
import com.tr.candlestickprovider.service.impl.InstrumentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CandlesticksControllerUnitTest {

    MockMvc mockMvc;

    InstrumentServiceImpl instrumentService;

    InstrumentHashServiceImpl instrumentHashService;

    InstrumentDocumentServiceImpl instrumentDocumentService;

    @BeforeEach
    public void setUp() {
        instrumentHashService =
                Mockito.mock(InstrumentHashServiceImpl.class);
        instrumentDocumentService =
                Mockito.mock(InstrumentDocumentServiceImpl.class);
        instrumentService = new InstrumentServiceImpl(instrumentHashService, instrumentDocumentService);
        mockMvc = MockMvcBuilders.standaloneSetup(new CandlesticksController(instrumentService)).build();
    }


    @Test
    public void testGetCandlesticks() throws Exception {
        testCandlestickControllerEndpoints("/candlesticks", CandlesticksController.LIMIT);

    }

    @Test
    public void testGetAllCandlesticks() throws Exception {
        testCandlestickControllerEndpoints("/candlesticks/get-all", 0);
    }

    public void testCandlestickControllerEndpoints(String endpoint, int limit) throws Exception {
        String isin = "isin";
        CandlestickDTO candlestickDTO = new CandlestickDTO();
        candlestickDTO.setIsin(isin);
        InstrumentDTO instrumentDTO = new InstrumentDTO();
        instrumentDTO.setIsin(isin);
        instrumentDTO.setCandlesticks(Arrays.asList(candlestickDTO));

        Mockito.when(instrumentService.hasInstrument(isin)).thenReturn(true);
        Mockito.when(instrumentHashService.hasInstrument(isin)).thenReturn(true);

        Mockito.when(instrumentService.getByIsin(isin, limit))
                .thenReturn(instrumentDTO);

        String result =
                mockMvc
                        .perform(get(endpoint).param(isin, isin))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
        String content = new ObjectMapper().writeValueAsString(Arrays.asList(candlestickDTO));
        assertEquals(result, content);


        Mockito.when(instrumentService.getByIsin(isin, CandlesticksController.LIMIT))
                .thenThrow(InstrumentNotFoundException.class);

        mockMvc.perform(get(endpoint).param(isin, isin))
                .andExpect(r -> assertThat(r.getResolvedException() instanceof InstrumentNotFoundException));
    }
}