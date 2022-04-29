package com.tr.candlestickprovider.service.impl;

import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.service.InstrumentService;
import com.tr.candlestickprovider.service.exceptions.InstrumentNotFoundException;
import com.tr.candlestickprovider.service.exceptions.InstrumentTypeException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Priority;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
public class InstrumentServiceImpl implements InstrumentService {

    private final InstrumentHashServiceImpl instrumentHashService;

    private final InstrumentDocumentServiceImpl instrumentDocumentService;

    public InstrumentServiceImpl(InstrumentHashServiceImpl instrumentHashService,
                                 InstrumentDocumentServiceImpl instrumentDocumentService) {
        this.instrumentHashService = instrumentHashService;
        this.instrumentDocumentService = instrumentDocumentService;
    }

    @Override
    public boolean hasInstrument(String isin) {
        return this.instrumentHashService.hasInstrument(isin);
    }

    @Override
    public InstrumentDTO getByIsin(String isin, int candlesticksLimit) throws InstrumentNotFoundException {
        if (hasInstrument(isin)) {
            return this.instrumentHashService.getByIsin(isin, candlesticksLimit);
        } else {
            InstrumentDTO instrumentDTO = this.instrumentDocumentService.getByIsin(isin, candlesticksLimit);
            this.instrumentHashService.save(instrumentDTO);
            return instrumentDTO;
        }
    }

    @Override
    public List<InstrumentDTO> getAll() {
        List<InstrumentDTO> instrumentDTOS = this.instrumentHashService.getAll();
        if (instrumentDTOS == null || instrumentDTOS.isEmpty()) {
            instrumentDTOS = this.instrumentDocumentService.getAll();
            if (instrumentDTOS == null || instrumentDTOS.isEmpty()) instrumentDTOS = new ArrayList<>();
        }
        return instrumentDTOS;
    }

    @Override
    public List<InstrumentDTO> getAllByType(Type type) throws InstrumentTypeException {
        if (type == null || !(type == Type.ADD || type == Type.DELETE))
            throw new InstrumentTypeException(type);
        List<InstrumentDTO> instrumentDTOS = this.instrumentHashService.getAllByType(type);
        if (instrumentDTOS == null || instrumentDTOS.isEmpty()) {
            instrumentDTOS = this.instrumentDocumentService.getAllByType(type);
            if (instrumentDTOS == null || instrumentDTOS.isEmpty()) return new ArrayList<>();
            else {
                this.instrumentHashService.saveAll(instrumentDTOS);
            }
        }
        return instrumentDTOS;
    }

    @Override
    public InstrumentDTO save(InstrumentDTO instrumentDTO) {
        this.instrumentDocumentService.save(instrumentDTO);
        return this.instrumentHashService.save(instrumentDTO);
    }

    @Override
    public void saveAll(List<InstrumentDTO> instrumentDTOS) {
        this.instrumentDocumentService.saveAll(instrumentDTOS);
        this.instrumentHashService.saveAll(instrumentDTOS);
    }

    @Override
    public void deleteByIsin(String isin) {
        this.instrumentDocumentService.deleteByIsin(isin);
        this.instrumentHashService.deleteByIsin(isin);
    }

    @Override
    public void deleteAll() {
        this.instrumentHashService.deleteAll();
        this.instrumentDocumentService.deleteAll();
    }
}
