package com.tr.candlestickprovider.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickprovider.model.dto.CandlestickDTO;
import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.service.exceptions.InstrumentNotFoundException;
import com.tr.candlestickprovider.service.impl.InstrumentDocumentServiceImpl;
import com.tr.candlestickprovider.service.impl.InstrumentHashServiceImpl;
import com.tr.candlestickprovider.service.impl.InstrumentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InstrumentControllerUnitTest {

    MockMvc mockMvc;

    InstrumentServiceImpl instrumentService;

    InstrumentHashServiceImpl instrumentHashService;

    InstrumentDocumentServiceImpl instrumentDocumentService;

    @BeforeEach
    void setUp() {

        instrumentHashService =
                Mockito.mock(InstrumentHashServiceImpl.class);
        instrumentDocumentService =
                Mockito.mock(InstrumentDocumentServiceImpl.class);
        instrumentService = new InstrumentServiceImpl(instrumentHashService, instrumentDocumentService);
        mockMvc = MockMvcBuilders.standaloneSetup(new InstrumentController(instrumentService)).build();
    }

    @Test
    void getInstrument() throws Exception {
        String isin = "isin";
        CandlestickDTO candlestickDTO = new CandlestickDTO();
        candlestickDTO.setIsin(isin);
        InstrumentDTO instrumentDTO = new InstrumentDTO();
        instrumentDTO.setIsin(isin);
        Map<String, CandlestickDTO> m = new HashMap<String, CandlestickDTO>();
        m.put(Instant.now().toString(), candlestickDTO);
        instrumentDTO.setCandlesticks(m);

        Mockito.when(instrumentService.hasInstrument(isin)).thenReturn(true);
        Mockito.when(instrumentHashService.hasInstrument(isin)).thenReturn(true);

        Mockito.when(instrumentService.getByIsin(isin, 0))
                .thenReturn(instrumentDTO);

        String result =
                mockMvc
                        .perform(get("/instruments/{isin}", isin))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
        String content = new ObjectMapper().writeValueAsString(instrumentDTO);
        assertEquals(result, content);


        Mockito.when(instrumentService.getByIsin(isin, CandlesticksController.LIMIT))
                .thenThrow(InstrumentNotFoundException.class);

        mockMvc.perform(get("/instruments/{isin}", isin))
                .andExpect(r -> assertThat(r.getResolvedException() instanceof InstrumentNotFoundException));
    }

    @Test
    void getAll() throws Exception {
        testInstrumentControllerGetALLEndpoints("/instruments/get-all", null);
    }

    @Test
    void getAllAdded() throws Exception {
        testInstrumentControllerGetALLEndpoints("/instruments/get-all-added", Type.ADD);
    }

    public void testInstrumentControllerGetALLEndpoints(String endpoint, Type type) throws Exception {
        String isin = "isin";
        CandlestickDTO candlestickDTO = new CandlestickDTO();
        candlestickDTO.setIsin(isin);
        // instrument 1
        InstrumentDTO instrumentDTO = new InstrumentDTO();
        instrumentDTO.setIsin(isin);
        Map<String, CandlestickDTO> m = new HashMap<String, CandlestickDTO>();
        m.put(Instant.now().toString(), candlestickDTO);
        instrumentDTO.setCandlesticks(m);

        //instrument 2
        InstrumentDTO instrumentDTO2 = new InstrumentDTO();
        instrumentDTO2.setIsin(isin);
        instrumentDTO2.setCandlesticks(m);

        Mockito.when(instrumentService.hasInstrument(isin)).thenReturn(true);
        Mockito.when(instrumentHashService.hasInstrument(isin)).thenReturn(true);

        List<InstrumentDTO> instrumentDTOList = new ArrayList<>(Arrays.asList(instrumentDTO, instrumentDTO2));

        Mockito.when(type == null ? instrumentService.getAll(): instrumentService.getAllByType(type))
                .thenReturn(instrumentDTOList);

        String result =
                mockMvc
                        .perform(get(endpoint))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
        String content = new ObjectMapper().writeValueAsString(instrumentDTOList);
        assertEquals(result, content);
    }
}