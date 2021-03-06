package com.tr.candlestickbuilder.model.mapper;

import com.tr.candlestickbuilder.model.dto.CandlestickDTO;
import com.tr.candlestickbuilder.model.mongodb.CandlestickDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CandlestickDocumentMapper extends EntityMapper<CandlestickDTO, CandlestickDocument>{
}
