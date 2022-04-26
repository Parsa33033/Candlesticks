package com.tr.candlestickprovider.model.mapper;

import com.tr.candlestickprovider.model.dto.CandlestickDTO;
import com.tr.candlestickprovider.model.redis.Candlestick;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CandlestickMapper extends EntityMapper<CandlestickDTO, Candlestick>{
}
