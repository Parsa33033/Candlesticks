package com.tr.candlestickbuilder.service;

import com.tr.candlestickbuilder.model.CandlestickDocument;
import com.tr.candlestickbuilder.model.InstrumentDocument;
import com.tr.candlestickbuilder.model.dto.CandlestickDTO;
import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.mapper.CandlestickDocumentMapper;
import com.tr.candlestickbuilder.model.mapper.CandlestickMapper;
import com.tr.candlestickbuilder.model.mapper.InstrumentDocumentMapper;
import com.tr.candlestickbuilder.model.mapper.InstrumentMapper;
import com.tr.candlestickbuilder.model.redis.Candlestick;
import com.tr.candlestickbuilder.model.redis.Instrument;
import com.tr.candlestickbuilder.repository.InstrumentDocumentRepository;
import com.tr.candlestickbuilder.repository.InstrumentRepository;
import com.tr.candlestickbuilder.service.exceptions.InstrumentNotFoundException;
import org.springframework.context.annotation.Bean;
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
        Optional<InstrumentDocument> instrumentDocumentOptional = this.instrumentDocumentRepository.findById(isin);
        if (instrumentDocumentOptional.isPresent()) {
            InstrumentDocument instrumentDocument = instrumentDocumentOptional.get();
            List<CandlestickDTO> candlestickDTOS = candlestickDocumentMapper.toDtos(instrumentDocument.getCandlesticks());
            InstrumentDTO instrumentDTO = instrumentDocumentMapper.toDto(instrumentDocument);
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
