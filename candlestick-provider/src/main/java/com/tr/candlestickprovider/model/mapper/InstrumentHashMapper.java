package com.tr.candlestickprovider.model.mapper;

import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.redis.InstrumentHash;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface InstrumentHashMapper extends EntityMapper<InstrumentDTO, InstrumentHash> {
}
