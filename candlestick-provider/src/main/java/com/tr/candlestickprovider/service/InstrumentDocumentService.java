package com.tr.candlestickprovider.service;

import com.tr.candlestickprovider.model.CandlestickDocument;
import com.tr.candlestickprovider.model.InstrumentDocument;
import com.tr.candlestickprovider.model.dto.CandlestickDTO;
import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.mapper.CandlestickDocumentMapper;
import com.tr.candlestickprovider.model.mapper.CandlestickMapper;
import com.tr.candlestickprovider.model.mapper.InstrumentDocumentMapper;
import com.tr.candlestickprovider.model.mapper.InstrumentMapper;
import com.tr.candlestickprovider.model.redis.Candlestick;
import com.tr.candlestickprovider.model.redis.Instrument;
import com.tr.candlestickprovider.repository.InstrumentDocumentRepository;
import com.tr.candlestickprovider.service.exceptions.InstrumentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class InstrumentDocumentService {

    private final InstrumentDocumentRepository instrumentDocumentRepository;
    private final InstrumentDocumentMapper instrumentDocumentMapper;

    private final CandlestickDocumentMapper candlestickDocumentMapper;

    public InstrumentDocumentService(InstrumentDocumentRepository instrumentDocumentRepository,
                                     InstrumentDocumentMapper instrumentDocumentMapper,
                                     CandlestickDocumentMapper candlestickDocumentMapper) {
        this.instrumentDocumentRepository = instrumentDocumentRepository;
        this.instrumentDocumentMapper = instrumentDocumentMapper;
        this.candlestickDocumentMapper = candlestickDocumentMapper;
    }

    public boolean hasInstrument(String isin) {
        return instrumentDocumentRepository.findById(isin).isPresent();
    }

    public InstrumentDTO getByIsin(String isin) {
        Optional<InstrumentDocument> instrumentOptional = this.instrumentDocumentRepository.findById(isin);
        if (instrumentOptional.isPresent()) {
            InstrumentDocument instrument = instrumentOptional.get();
            List<CandlestickDTO> candlestickDTOS = candlestickDocumentMapper.toDtos(instrument.getCandlesticks());
            InstrumentDTO instrumentDTO = instrumentDocumentMapper.toDto(instrument);
            instrumentDTO.setCandlesticks(candlestickDTOS);
            return instrumentDTO;
        } else {
            throw new InstrumentNotFoundException(isin);
        }
    }

    public List<InstrumentDTO> getAll() {
        return StreamSupport.stream(instrumentDocumentRepository.findAll().spliterator(), false)
                .map(instrumentDocumentMapper::toDto).collect(Collectors.toList());
    }

    public InstrumentDTO save(InstrumentDTO instrumentDTO) {
        InstrumentDocument instrumentDocument = instrumentDocumentMapper.toEntity(instrumentDTO);
        List<CandlestickDocument> candlesticks = candlestickDocumentMapper.toEntities(instrumentDTO.getCandlesticks());
        instrumentDocument.setCandlesticks(candlesticks);
        instrumentDocument = this.instrumentDocumentRepository.save(instrumentDocument);
        return instrumentDocumentMapper.toDto(instrumentDocument);
    }

    public void removeById(String isin) {
        this.instrumentDocumentRepository.deleteById(isin);
    }
}
