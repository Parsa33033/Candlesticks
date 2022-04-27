package com.tr.candlestickprovider.model.mapper;

import com.tr.candlestickprovider.model.dto.CandlestickDTO;
import com.tr.candlestickprovider.model.redis.CandlestickHash;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CandlestickHashMapper extends EntityMapper<CandlestickDTO, CandlestickHash>{
}
