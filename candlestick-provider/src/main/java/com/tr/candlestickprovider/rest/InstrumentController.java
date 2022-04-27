package com.tr.candlestickprovider.rest;

import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.service.impl.InstrumentHashServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Instrument Controller
 */
@RestController
public class InstrumentController {

    private final InstrumentHashServiceImpl instrumentService;

    public InstrumentController(InstrumentHashServiceImpl instrumentService) {
        this.instrumentService = instrumentService;
    }

    /**
     * Gets all available instruments in the catalogue
     * @return
     */
    @GetMapping("/instruments/get-all")
    public List<InstrumentDTO> getAll() {
        return this.instrumentService.getAll();
    }

    /**
     * Gets all available instruments in the catalogue
     * @return
     */
    @GetMapping("/instruments/get-all-added")
    public List<InstrumentDTO> getAllAdded() {
        return this.instrumentService.getAllByType(Type.ADD);
    }
}
