package com.tr.candlestickprovider.rest;

import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.service.InstrumentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InstrumentController {

    private final InstrumentService instrumentService;

    public InstrumentController(InstrumentService instrumentService) {
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
        return this.instrumentService.getAllAdded();
    }

    /**
     * Gets the count of instruments in the catalogue
     * @return
     */
    @GetMapping("/isntruments/get-ount")
    public long getCount() {
        return this.instrumentService.getCount();
    }
}
