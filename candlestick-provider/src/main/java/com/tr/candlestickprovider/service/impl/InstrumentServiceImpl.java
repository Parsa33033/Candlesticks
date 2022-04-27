package com.tr.candlestickprovider.service.impl;

import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.service.InstrumentService;
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
    public InstrumentDTO getByIsin(String isin, int candlesticksLimit) {
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
        return getAllChoose(null);
    }

    @Override
    public List<InstrumentDTO> getAllByType(Type type) {
        return getAllChoose(type);
    }

    public List<InstrumentDTO> getAllChoose(Type type) {
        List<InstrumentDTO> instrumentDTOS = this.instrumentHashService.getAll();
        if (instrumentDTOS == null || instrumentDTOS.isEmpty()) {
            instrumentDTOS = this.instrumentDocumentService.getAll();
            if (instrumentDTOS == null || instrumentDTOS.isEmpty()) return new ArrayList<>();
            else {
                this.instrumentHashService.saveAll(instrumentDTOS);
            }
        }
        if (type == null)
            return instrumentDTOS;
        else
            return instrumentDTOS.stream()
                    .filter(instrumentDTO -> instrumentDTO.getType() == type)
                    .collect(Collectors.toList());
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
}
