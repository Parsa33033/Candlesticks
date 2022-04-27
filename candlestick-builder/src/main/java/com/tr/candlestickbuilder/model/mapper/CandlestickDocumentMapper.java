package com.tr.candlestickbuilder.model.mapper;

import com.tr.candlestickbuilder.model.CandlestickDocument;
import com.tr.candlestickbuilder.model.dto.CandlestickDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CandlestickDocumentMapper extends EntityMapper<CandlestickDTO, CandlestickDocument>{
}
