package com.tr.candlestickbuilder.service;

import com.tr.candlestickbuilder.model.dto.CandlestickDTO;

import java.util.List;

public interface CandlestickService {

    List<CandlestickDTO> getCandleSticksByIsin(String isin);

    List<CandlestickDTO> getAll();

}
