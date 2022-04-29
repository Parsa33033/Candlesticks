package com.tr.candlestickprovider.rest;

import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.service.InstrumentService;
import com.tr.candlestickprovider.service.exceptions.InstrumentTypeException;
import com.tr.candlestickprovider.service.impl.InstrumentHashServiceImpl;
import com.tr.candlestickprovider.service.impl.InstrumentServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Instrument Controller
 */
@RestController
public class InstrumentController {

    private final InstrumentService instrumentService;

    public InstrumentController(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    /**
     * Gets an instrument by its isin
     * @param isin
     * @return
     */
    @GetMapping("/instruments/{isin}")
    public InstrumentDTO getInstrument(@PathVariable(value = "isin", required = true) String isin) {
        return this.instrumentService.getByIsin(isin, 0);
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
    public List<InstrumentDTO> getAllAdded() throws InstrumentTypeException {
        return this.instrumentService.getAllByType(Type.ADD);
    }
}
