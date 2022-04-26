package com.tr.candlestickprovider.service;

import com.tr.candlestickprovider.model.dto.CandlestickDTO;
import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.model.mapper.CandlestickMapper;
import com.tr.candlestickprovider.model.mapper.InstrumentMapper;
import com.tr.candlestickprovider.model.redis.Candlestick;
import com.tr.candlestickprovider.model.redis.Instrument;
import com.tr.candlestickprovider.repository.InstrumentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;

    private final InstrumentMapper instrumentMapper;

    private final InstrumentDocumentService instrumentDocumentService;

    private final CandlestickMapper candlestickMapper;

    public InstrumentService(InstrumentRepository instrumentRepository,
                             InstrumentMapper instrumentMapper,
                             InstrumentDocumentService instrumentDocumentService,
                             CandlestickMapper candlestickMapper) {
        this.instrumentRepository = instrumentRepository;
        this.instrumentMapper = instrumentMapper;
        this.instrumentDocumentService = instrumentDocumentService;
        this.candlestickMapper = candlestickMapper;
    }

    public InstrumentDTO getByIsin(String isin, int limit) {
        Optional<Instrument> instrumentOptional = this.instrumentRepository.findById(isin);
        if (instrumentOptional.isPresent()) {
            Instrument instrument = instrumentOptional.get();
            List<CandlestickDTO> candlestickDTOS =
                    candlestickMapper.toDtos(instrument.getCandlesticks());
            if (candlestickDTOS == null)
                candlestickDTOS = new ArrayList<>();
            if (!candlestickDTOS.isEmpty() && limit > 0 && limit <= candlestickDTOS.size())
                candlestickDTOS = candlestickDTOS.stream().limit(limit).collect(Collectors.toList());
            InstrumentDTO instrumentDTO = instrumentMapper.toDto(instrument);
            instrumentDTO.setCandlesticks(candlestickDTOS);
            return instrumentDTO;
        } else {
            InstrumentDTO instrumentDTO = this.instrumentDocumentService.getByIsin(isin);
            List<CandlestickDTO> candlestickDTOS = instrumentDTO.getCandlesticks();
            if (candlestickDTOS == null)
                candlestickDTOS = new ArrayList<>();
            if (!candlestickDTOS.isEmpty() && limit > 0 && limit <= candlestickDTOS.size()) {
                candlestickDTOS = candlestickDTOS.stream().limit(limit).collect(Collectors.toList());
                instrumentDTO.setCandlesticks(candlestickDTOS);
            }
            instrumentRepository.save(instrumentMapper.toEntity(instrumentDTO));
            return instrumentDTO;
        }
    }

    public List<InstrumentDTO> getAll() {
        return StreamSupport.stream(instrumentRepository.findAll().spliterator(), false)
                .map(instrumentMapper::toDto).collect(Collectors.toList());
    }

    public List<InstrumentDTO> getAllAdded() {
        return StreamSupport.stream(instrumentRepository.findAll().spliterator(), false)
                .filter(instrument -> instrument.getType() == Type.ADD)
                .map(instrumentMapper::toDto).collect(Collectors.toList());
    }

    public long getCount() {
        return instrumentRepository.findAll().spliterator().getExactSizeIfKnown();
    }

    public InstrumentDTO save(InstrumentDTO instrumentDTO) {
        Instrument instrument = instrumentMapper.toEntity(instrumentDTO);
        List<Candlestick> candlesticks = candlestickMapper.toEntities(instrumentDTO.getCandlesticks());
        instrument.setCandlesticks(candlesticks);
        instrument = this.instrumentRepository.save(instrument);
        this.instrumentDocumentService.save(instrumentDTO);
        return instrumentMapper.toDto(instrument);
    }

    public void removeById(String isin) {
        this.instrumentRepository.deleteById(isin);
    }
}
