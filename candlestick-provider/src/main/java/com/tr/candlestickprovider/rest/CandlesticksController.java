package com.tr.candlestickprovider.rest;

import com.tr.candlestickprovider.model.dto.CandlestickDTO;
import com.tr.candlestickprovider.service.InstrumentService;
import com.tr.candlestickprovider.service.impl.InstrumentHashServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Candlestick Controller
 */
@RestController
public class CandlesticksController {
    private final static int LIMIT = 30;
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
    public List<CandlestickDTO> getCandlesticks(@RequestParam("isin") String isin) {
        return instrumentService.getByIsin(isin, LIMIT).getCandlesticks();
    }

    /**
     * Get an instrument candlesticks list
     * @param isin
     * @return
     */
    @GetMapping("/candlesticks/get-all")
    public List<CandlestickDTO> getAllCandlesticks(@RequestParam("isin") String isin) {
        return instrumentService.getByIsin(isin, 0).getCandlesticks();
    }


}
