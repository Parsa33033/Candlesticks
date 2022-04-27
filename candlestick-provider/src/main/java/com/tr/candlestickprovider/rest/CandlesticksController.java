package com.tr.candlestickprovider.rest;

import com.tr.candlestickprovider.model.dto.CandlestickDTO;
import com.tr.candlestickprovider.service.CandlestickService;
import com.tr.candlestickprovider.service.InstrumentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CandlesticksController {
    private final static int LIMIT = 30;
    private final CandlestickService candlestickService;
    private final InstrumentService instrumentService;

    public CandlesticksController(CandlestickService candlestickService,
                                  InstrumentService instrumentService) {
        this.candlestickService = candlestickService;
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
    public List<CandlestickDTO> getCandleSticks(@RequestParam("isin") String isin) {
        return instrumentService.getByIsin(isin, LIMIT).getCandlesticks();
    }


}
