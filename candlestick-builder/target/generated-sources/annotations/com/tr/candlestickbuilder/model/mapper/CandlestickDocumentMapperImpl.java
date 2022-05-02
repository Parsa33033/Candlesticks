package com.tr.candlestickbuilder.model.mapper;

import com.tr.candlestickbuilder.model.dto.CandlestickDTO;
import com.tr.candlestickbuilder.model.mongodb.CandlestickDocument;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-02T19:34:35+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class CandlestickDocumentMapperImpl implements CandlestickDocumentMapper {

    @Override
    public CandlestickDTO toDto(CandlestickDocument entity) {
        if ( entity == null ) {
            return null;
        }

        CandlestickDTO candlestickDTO = new CandlestickDTO();

        candlestickDTO.setIsin( entity.getIsin() );
        if ( entity.getOpenTimestamp() != null ) {
            candlestickDTO.setOpenTimestamp( entity.getOpenTimestamp().toString() );
        }
        candlestickDTO.setOpenPrice( entity.getOpenPrice() );
        candlestickDTO.setHighPrice( entity.getHighPrice() );
        candlestickDTO.setLowPrice( entity.getLowPrice() );
        candlestickDTO.setClosingPrice( entity.getClosingPrice() );
        if ( entity.getCloseTimestamp() != null ) {
            candlestickDTO.setCloseTimestamp( entity.getCloseTimestamp().toString() );
        }

        return candlestickDTO;
    }

    @Override
    public CandlestickDocument toEntity(CandlestickDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CandlestickDocument candlestickDocument = new CandlestickDocument();

        if ( dto.getOpenTimestamp() != null ) {
            candlestickDocument.setOpenTimestamp( Instant.parse( dto.getOpenTimestamp() ) );
        }
        candlestickDocument.setOpenPrice( dto.getOpenPrice() );
        candlestickDocument.setHighPrice( dto.getHighPrice() );
        candlestickDocument.setLowPrice( dto.getLowPrice() );
        candlestickDocument.setClosingPrice( dto.getClosingPrice() );
        if ( dto.getCloseTimestamp() != null ) {
            candlestickDocument.setCloseTimestamp( Instant.parse( dto.getCloseTimestamp() ) );
        }
        candlestickDocument.setIsin( dto.getIsin() );

        return candlestickDocument;
    }

    @Override
    public List<CandlestickDTO> toDtoLists(List<CandlestickDocument> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CandlestickDTO> list = new ArrayList<CandlestickDTO>( entities.size() );
        for ( CandlestickDocument candlestickDocument : entities ) {
            list.add( toDto( candlestickDocument ) );
        }

        return list;
    }

    @Override
    public List<CandlestickDocument> toEntityLists(List<CandlestickDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<CandlestickDocument> list = new ArrayList<CandlestickDocument>( dtos.size() );
        for ( CandlestickDTO candlestickDTO : dtos ) {
            list.add( toEntity( candlestickDTO ) );
        }

        return list;
    }

    @Override
    public Map<String, CandlestickDTO> toDtoMaps(Map<String, CandlestickDocument> entities) {
        if ( entities == null ) {
            return null;
        }

        Map<String, CandlestickDTO> map = new HashMap<String, CandlestickDTO>( Math.max( (int) ( entities.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, CandlestickDocument> entry : entities.entrySet() ) {
            String key = entry.getKey();
            CandlestickDTO value = toDto( entry.getValue() );
            map.put( key, value );
        }

        return map;
    }

    @Override
    public Map<String, CandlestickDocument> toEntityMaps(Map<String, CandlestickDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        Map<String, CandlestickDocument> map = new HashMap<String, CandlestickDocument>( Math.max( (int) ( dtos.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, CandlestickDTO> entry : dtos.entrySet() ) {
            String key = entry.getKey();
            CandlestickDocument value = toEntity( entry.getValue() );
            map.put( key, value );
        }

        return map;
    }
}
