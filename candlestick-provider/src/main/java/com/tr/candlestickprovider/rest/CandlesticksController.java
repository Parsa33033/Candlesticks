package com.tr.candlestickprovider.rest;

import com.tr.candlestickprovider.model.dto.CandlestickDTO;
import com.tr.candlestickprovider.service.InstrumentService;
import com.tr.candlestickprovider.service.exceptions.InstrumentNotFoundException;
import com.tr.candlestickprovider.service.impl.InstrumentHashServiceImpl;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Candlestick Controller
 */
@RestController
public class CandlesticksController {
    public final static int LIMIT = 30;
    private final InstrumentService instrumentService;

    public CandlesticksController(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    /**
     * Gets a specific instrument's candlesticks within the past 30 minutes
     * if less than 30 elements are in the past minute, adds from before 30minutes
     * until the size of the list is 30
     * @param isin
     * @return
     */
    @GetMapping("/candlesticks")
    public List<CandlestickDTO> getCandlesticks(@RequestParam(value = "isin", required = true) String isin)
            throws InstrumentNotFoundException {
        return instrumentService.getByIsin(isin, LIMIT).getCandlesticks()
                .values().stream().collect(Collectors.toList());
    }

    /**
     * Get an instrument candlesticks list
     * @param isin
     * @return
     */
    @GetMapping("/candlesticks/get-all")
    public List<CandlestickDTO> getAllCandlesticks(@RequestParam(value = "isin", required = true) String isin)
            throws InstrumentNotFoundException {
        return instrumentService.getByIsin(isin, 0).getCandlesticks()
                .values().stream().collect(Collectors.toList());
    }


}
