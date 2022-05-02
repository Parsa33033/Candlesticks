package com.tr.candlestickbuilder.model.mapper;

import com.tr.candlestickbuilder.model.dto.CandlestickDTO;
import com.tr.candlestickbuilder.model.redis.CandlestickHash;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-02T19:41:41+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class CandlestickHashMapperImpl implements CandlestickHashMapper {

    @Override
    public CandlestickDTO toDto(CandlestickHash entity) {
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
    public CandlestickHash toEntity(CandlestickDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CandlestickHash candlestickHash = new CandlestickHash();

        if ( dto.getOpenTimestamp() != null ) {
            candlestickHash.setOpenTimestamp( Instant.parse( dto.getOpenTimestamp() ) );
        }
        candlestickHash.setOpenPrice( dto.getOpenPrice() );
        candlestickHash.setHighPrice( dto.getHighPrice() );
        candlestickHash.setLowPrice( dto.getLowPrice() );
        candlestickHash.setClosingPrice( dto.getClosingPrice() );
        if ( dto.getCloseTimestamp() != null ) {
            candlestickHash.setCloseTimestamp( Instant.parse( dto.getCloseTimestamp() ) );
        }
        candlestickHash.setIsin( dto.getIsin() );

        return candlestickHash;
    }

    @Override
    public List<CandlestickDTO> toDtoLists(List<CandlestickHash> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CandlestickDTO> list = new ArrayList<CandlestickDTO>( entities.size() );
        for ( CandlestickHash candlestickHash : entities ) {
            list.add( toDto( candlestickHash ) );
        }

        return list;
    }

    @Override
    public List<CandlestickHash> toEntityLists(List<CandlestickDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<CandlestickHash> list = new ArrayList<CandlestickHash>( dtos.size() );
        for ( CandlestickDTO candlestickDTO : dtos ) {
            list.add( toEntity( candlestickDTO ) );
        }

        return list;
    }

    @Override
    public Map<String, CandlestickDTO> toDtoMaps(Map<String, CandlestickHash> entities) {
        if ( entities == null ) {
            return null;
        }

        Map<String, CandlestickDTO> map = new HashMap<String, CandlestickDTO>( Math.max( (int) ( entities.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, CandlestickHash> entry : entities.entrySet() ) {
            String key = entry.getKey();
            CandlestickDTO value = toDto( entry.getValue() );
            map.put( key, value );
        }

        return map;
    }

    @Override
    public Map<String, CandlestickHash> toEntityMaps(Map<String, CandlestickDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        Map<String, CandlestickHash> map = new HashMap<String, CandlestickHash>( Math.max( (int) ( dtos.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, CandlestickDTO> entry : dtos.entrySet() ) {
            String key = entry.getKey();
            CandlestickHash value = toEntity( entry.getValue() );
            map.put( key, value );
        }

        return map;
    }
}
