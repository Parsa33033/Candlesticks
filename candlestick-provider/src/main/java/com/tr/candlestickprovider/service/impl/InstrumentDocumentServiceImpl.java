package com.tr.candlestickprovider.service.impl;

import com.tr.candlestickprovider.model.dto.CandlestickDTO;
import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.model.mapper.CandlestickDocumentMapper;
import com.tr.candlestickprovider.model.mapper.InstrumentDocumentMapper;
import com.tr.candlestickprovider.model.mongodb.CandlestickDocument;
import com.tr.candlestickprovider.model.mongodb.InstrumentDocument;
import com.tr.candlestickprovider.repository.InstrumentDocumentRepository;
import com.tr.candlestickprovider.service.InstrumentService;
import com.tr.candlestickprovider.service.exceptions.InstrumentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class InstrumentDocumentServiceImpl implements InstrumentService {

    private final InstrumentDocumentRepository instrumentDocumentRepository;

    private final InstrumentDocumentMapper instrumentDocumentMapper;

    private final CandlestickDocumentMapper candlestickDocumentMapper;

    public InstrumentDocumentServiceImpl(InstrumentDocumentRepository instrumentDocumentRepository,
                                         InstrumentDocumentMapper instrumentDocumentMapper,
                                         CandlestickDocumentMapper candlestickDocumentMapper) {
        this.instrumentDocumentRepository = instrumentDocumentRepository;
        this.instrumentDocumentMapper = instrumentDocumentMapper;
        this.candlestickDocumentMapper = candlestickDocumentMapper;
    }

    @Override
    public boolean hasInstrument(String isin) {
        return false;
    }

    /**
     * @param isin
     * @param candlesticksLimit 0 for fetching all candlesticks, otherwise fetch a list of size candlestickLimit
     * @return
     */
    @Override
    public InstrumentDTO getByIsin(String isin, int candlesticksLimit) throws InstrumentNotFoundException {
        Optional<InstrumentDocument> instrumentOptional = this.instrumentDocumentRepository.findById(isin);
        if (instrumentOptional.isPresent()) {
            InstrumentDocument instrument = instrumentOptional.get();
            Map<String, CandlestickDTO> candlestickDTOS = candlestickDocumentMapper.toDtoMaps(instrument.getCandlesticks());
            if (candlestickDTOS == null)
                candlestickDTOS = new HashMap<>();
            if (!candlestickDTOS.isEmpty() &&
                    candlesticksLimit > 0 &&
                    candlesticksLimit < candlestickDTOS.size()) {
                candlestickDTOS = candlestickDTOS.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                        .limit(candlesticksLimit)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            } else {
                candlestickDTOS = candlestickDTOS.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            }
            InstrumentDTO instrumentDTO = instrumentDocumentMapper.toDto(instrument);
            instrumentDTO.setCandlesticks(candlestickDTOS);
            return instrumentDTO;
        } else {
            throw new InstrumentNotFoundException(isin);
        }
    }

    @Override
    public List<InstrumentDTO> getAll() {
        return StreamSupport.stream(instrumentDocumentRepository.findAll().spliterator(), false)
                .map(instrumentDocumentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<InstrumentDTO> getAllByType(Type type) {
        return StreamSupport.stream(instrumentDocumentRepository.findAll().spliterator(), false)
                .filter(instrument -> instrument.getType() == type)
                .map(instrumentDocumentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public InstrumentDTO save(InstrumentDTO instrumentDTO) {
        InstrumentDocument instrumentDocument = instrumentDocumentMapper.toEntity(instrumentDTO);
        Map<String, CandlestickDocument> candlesticks = candlestickDocumentMapper.toEntityMaps(instrumentDTO.getCandlesticks());
        instrumentDocument.setCandlesticks(candlesticks);
        instrumentDocument = this.instrumentDocumentRepository.save(instrumentDocument);
        return instrumentDocumentMapper.toDto(instrumentDocument);
    }

    @Override
    public void saveAll(List<InstrumentDTO> instrumentDTOS) {
        this.instrumentDocumentRepository.saveAll(instrumentDocumentMapper.toEntityLists(instrumentDTOS));
    }

    @Override
    public void deleteByIsin(String isin) {
        this.instrumentDocumentRepository.deleteById(isin);
    }

    @Override
    public void deleteAll() {
        this.instrumentDocumentRepository.deleteAll();
    }
}
