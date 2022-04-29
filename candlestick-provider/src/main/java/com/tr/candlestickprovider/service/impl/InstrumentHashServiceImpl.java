package com.tr.candlestickprovider.service.impl;

import com.tr.candlestickprovider.model.dto.CandlestickDTO;
import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.model.mapper.CandlestickHashMapper;
import com.tr.candlestickprovider.model.mapper.InstrumentHashMapper;
import com.tr.candlestickprovider.model.redis.CandlestickHash;
import com.tr.candlestickprovider.model.redis.InstrumentHash;
import com.tr.candlestickprovider.repository.InstrumentHashRepository;
import com.tr.candlestickprovider.service.InstrumentService;
import com.tr.candlestickprovider.service.exceptions.InstrumentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class InstrumentHashServiceImpl implements InstrumentService {

    private final InstrumentHashRepository instrumentHashRepository;

    private final InstrumentHashMapper instrumentHashMapper;

    private final CandlestickHashMapper candlestickHashMapper;

    public InstrumentHashServiceImpl(InstrumentHashRepository instrumentHashRepository,
                                     InstrumentHashMapper instrumentHashMapper,
                                     CandlestickHashMapper candlestickHashMapper) {
        this.instrumentHashRepository = instrumentHashRepository;
        this.instrumentHashMapper = instrumentHashMapper;
        this.candlestickHashMapper = candlestickHashMapper;
    }

    @Override
    public boolean hasInstrument(String isin) {
        return this.instrumentHashRepository.findById(isin).isPresent();
    }

    @Override
    public InstrumentDTO getByIsin(String isin, int candlesticksLimit) throws InstrumentNotFoundException {
        if (hasInstrument(isin)) {
            InstrumentHash instrument = this.instrumentHashRepository.findById(isin).get();
            List<CandlestickDTO> candlestickDTOS =
                    candlestickHashMapper.toDtos(instrument.getCandlesticks());
            if (candlestickDTOS == null)
                candlestickDTOS = new ArrayList<>();
            if (!candlestickDTOS.isEmpty() &&
                    candlesticksLimit > 0 &&
                    candlesticksLimit <= candlestickDTOS.size()) {
                candlestickDTOS = candlestickDTOS.stream().limit(candlesticksLimit).collect(Collectors.toList());
            }
            InstrumentDTO instrumentDTO = instrumentHashMapper.toDto(instrument);
            instrumentDTO.setCandlesticks(candlestickDTOS);
            return instrumentDTO;
        } else {
            throw new InstrumentNotFoundException(isin);
        }
    }

    @Override
    public List<InstrumentDTO> getAll() {
        return StreamSupport.stream(instrumentHashRepository.findAll().spliterator(), false)
                .map(instrumentHashMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<InstrumentDTO> getAllByType(Type type) {
        return StreamSupport.stream(instrumentHashRepository.findAll().spliterator(), false)
                .filter(instrument -> instrument.getType() == type)
                .map(instrumentHashMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public InstrumentDTO save(InstrumentDTO instrumentDTO) {
        InstrumentHash instrument = instrumentHashMapper.toEntity(instrumentDTO);
        List<CandlestickHash> candlesticks = candlestickHashMapper.toEntities(instrumentDTO.getCandlesticks());
        instrument.setCandlesticks(candlesticks);
        instrument = this.instrumentHashRepository.save(instrument);
        return instrumentHashMapper.toDto(instrument);
    }

    @Override
    public void saveAll(List<InstrumentDTO> instrumentDTOS) {
        this.instrumentHashRepository.saveAll(instrumentHashMapper.toEntities(instrumentDTOS));
    }

    @Override
    public void deleteByIsin(String isin) {
        this.instrumentHashRepository.deleteById(isin);
    }

    @Override
    public void deleteAll() {
        this.instrumentHashRepository.deleteAll();
    }
}
