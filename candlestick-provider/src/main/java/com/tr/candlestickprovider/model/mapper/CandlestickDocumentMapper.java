package com.tr.candlestickprovider.model.mapper;

import com.tr.candlestickprovider.model.mongodb.CandlestickDocument;
import com.tr.candlestickprovider.model.dto.CandlestickDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CandlestickDocumentMapper extends EntityMapper<CandlestickDTO, CandlestickDocument>{
}
