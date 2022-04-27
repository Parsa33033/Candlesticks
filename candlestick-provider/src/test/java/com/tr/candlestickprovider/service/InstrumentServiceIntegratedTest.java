//package com.tr.candlestickprovider.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tr.candlestickprovider.model.dto.CandlestickDTO;
//import com.tr.candlestickprovider.model.dto.InstrumentDTO;
//import com.tr.candlestickprovider.model.dto.InstrumentEventDTO;
//import com.tr.candlestickprovider.model.enums.Type;
//import com.tr.candlestickprovider.repository.InstrumentDocumentRepository;
//import com.tr.candlestickprovider.repository.InstrumentHashRepository;
//import com.tr.candlestickprovider.service.impl.InstrumentHashServiceImpl;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//class InstrumentServiceIntegratedTest {
//    Logger logger = LoggerFactory.getLogger(InstrumentServiceIntegratedTest.class);
//
//    private final static String ISIN = "123456";
//    private final static String DESCRIPTION = "test";
//    private final static Type ADD = Type.ADD;
//    private final static Type DELETE = Type.ADD;
//
//    InstrumentEventDTO[] instrumentEvents;
//
//    @Autowired
//    InstrumentHashServiceImpl instrumentService;
//
//    @Autowired
//    InstrumentHashRepository instrumentRepository;
//
//    @Autowired
//    InstrumentDocumentRepository instrumentDocumentRepository;
//
//    @Autowired
//    ResourceLoader resourceLoader;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setUp() throws IOException {
//        URL url = resourceLoader.getResource("classpath:./test/instruments.json").getURL();
//        instrumentEvents = objectMapper.readValue(url, InstrumentEventDTO[].class);
//    }
//
//    @AfterEach
//    public void after() {
//
//    }
//
//    @Test
//    void getByIsin() {
//        for (InstrumentEventDTO instrumentEventDTO: instrumentEvents) {
//            InstrumentDTO tmp = saveInstrumentDTO(instrumentEventDTO.getInstrumentDTO());
//            logger.info("===> {}", tmp);
//            deleteInstrument(tmp.getIsin());
//        }
//    }
//
//    @Test
//    void getAll() {
//    }
//
//    @Test
//    void getAllAdded() {
//    }
//
//    @Test
//    void getCount() {
//    }
//
//    public InstrumentDTO createInstrumentDTO(String isin,
//                                    String description,
//                                    Type type,
//                                    List<CandlestickDTO> candlestickDTOList) {
//        InstrumentDTO instrumentDTO = new InstrumentDTO();
//        instrumentDTO.setIsin(ISIN);
//        instrumentDTO.setDescription(DESCRIPTION);
//        instrumentDTO.setType(ADD);
//        instrumentDTO.setCandlesticks(new ArrayList<>());
//        return instrumentDTO;
//    }
//
//    public InstrumentDTO saveInstrumentDTO(InstrumentDTO instrumentDTO) {
//        return instrumentService.save(instrumentDTO);
//    }
//
//    public void deleteInstrument(String isin) {
//        instrumentService.removeById(ISIN);
//        instrumentDocumentRepository.deleteById(ISIN);
//    }
//
//}