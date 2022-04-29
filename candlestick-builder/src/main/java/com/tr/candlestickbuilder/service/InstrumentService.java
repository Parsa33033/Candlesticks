package com.tr.candlestickbuilder.service;

import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.enums.Type;
import com.tr.candlestickbuilder.service.exceptions.InstrumentNotFoundException;
import com.tr.candlestickbuilder.service.exceptions.InstrumentTypeException;

import java.util.List;

public interface InstrumentService {

    boolean hasInstrument(String isin);

    InstrumentDTO getByIsin(String isin, int candlesticksLimit) throws InstrumentNotFoundException;

    List<InstrumentDTO> getAll();

    List<InstrumentDTO> getAllByType(Type type) throws InstrumentTypeException;

    InstrumentDTO save(InstrumentDTO instrumentDTO);

    void saveAll(List<InstrumentDTO> instrumentDTOS);

    void deleteByIsin(String isin);

    void deleteAll();
}
