package com.tr.candlestickbuilder.service;

import com.tr.candlestickbuilder.model.dto.CandlestickDTO;
import com.tr.candlestickbuilder.model.mapper.CandlestickMapper;
import com.tr.candlestickbuilder.model.redis.Candlestick;
import com.tr.candlestickbuilder.repository.CandlestickRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CandlestickService {

    private final CandlestickRepository candlestickRepository;

    private final CandlestickMapper candlestickMapper;

    public CandlestickService(CandlestickRepository candlestickRepository,
                              CandlestickMapper candlestickMapper) {
        this.candlestickRepository = candlestickRepository;
        this.candlestickMapper = candlestickMapper;
    }

    public boolean hasCandleStick(String isin) {
        return candlestickRepository.findById(isin).isPresent();
    }

    @Transactional(readOnly = true)
    public CandlestickDTO getCandleStickById(String isin) {
        return candlestickMapper.toDto(candlestickRepository.findById(isin).get());
    }

    @Transactional(readOnly = true)
    public List<CandlestickDTO> getAll() {
        return StreamSupport.stream(candlestickRepository.findAll().spliterator(), false)
                .map(candlestickMapper::toDto).collect(Collectors.toList());
    }

    public void save(CandlestickDTO candlestickDTO) {
        Candlestick candlestick = candlestickMapper.toEntity(candlestickDTO);
        candlestickRepository.save(candlestick);
    }

    public void deleteById(String isin) {
        candlestickRepository.deleteById(isin);
    }

    public void deleteAll() {
        candlestickRepository.deleteAll();
    }
}
