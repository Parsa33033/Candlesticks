package com.tr.candlestickbuilder.model.mapper;

import com.tr.candlestickbuilder.model.dto.CandlestickDTO;
import com.tr.candlestickbuilder.model.redis.CandlestickHash;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CandlestickHashMapper extends EntityMapper<CandlestickDTO, CandlestickHash>{
}
