package com.tr.candlestickprovider.service;

import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.service.exceptions.InstrumentTypeException;

import java.util.List;

public interface InstrumentService {

    boolean hasInstrument(String isin);

    InstrumentDTO getByIsin(String isin, int candlesticksLimit);

    List<InstrumentDTO> getAll();

    List<InstrumentDTO> getAllByType(Type type) throws InstrumentTypeException;

    InstrumentDTO save(InstrumentDTO instrumentDTO);

    void saveAll(List<InstrumentDTO> instrumentDTOS);

    void deleteByIsin(String isin);

    void deleteAll();
}
