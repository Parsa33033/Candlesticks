package com.tr.candlestickprovider.service;

import com.tr.candlestickprovider.model.dto.CandlestickDTO;

import java.util.List;

public interface CandlestickService {

    List<CandlestickDTO> getCandleSticksByIsin(String isin);

    List<CandlestickDTO> getAll();

}
