package com.tr.candlestickbuilder.model.mapper;

import com.tr.candlestickbuilder.model.dto.CandlestickDTO;
import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.redis.CandlestickHash;
import com.tr.candlestickbuilder.model.redis.InstrumentHash;
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
public class InstrumentHashMapperImpl implements InstrumentHashMapper {

    @Override
    public InstrumentDTO toDto(InstrumentHash entity) {
        if ( entity == null ) {
            return null;
        }

        InstrumentDTO instrumentDTO = new InstrumentDTO();

        instrumentDTO.setDescription( entity.getDescription() );
        instrumentDTO.setIsin( entity.getIsin() );
        instrumentDTO.setType( entity.getType() );
        instrumentDTO.setCandlesticks( stringCandlestickHashMapToStringCandlestickDTOMap( entity.getCandlesticks() ) );
        instrumentDTO.setTimestamp( entity.getTimestamp() );

        return instrumentDTO;
    }

    @Override
    public InstrumentHash toEntity(InstrumentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        InstrumentHash instrumentHash = new InstrumentHash();

        instrumentHash.setDescription( dto.getDescription() );
        instrumentHash.setType( dto.getType() );
        instrumentHash.setTimestamp( dto.getTimestamp() );
        instrumentHash.setIsin( dto.getIsin() );
        instrumentHash.setCandlesticks( stringCandlestickDTOMapToStringCandlestickHashMap( dto.getCandlesticks() ) );

        return instrumentHash;
    }

    @Override
    public List<InstrumentDTO> toDtoLists(List<InstrumentHash> entities) {
        if ( entities == null ) {
            return null;
        }

        List<InstrumentDTO> list = new ArrayList<InstrumentDTO>( entities.size() );
        for ( InstrumentHash instrumentHash : entities ) {
            list.add( toDto( instrumentHash ) );
        }

        return list;
    }

    @Override
    public List<InstrumentHash> toEntityLists(List<InstrumentDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<InstrumentHash> list = new ArrayList<InstrumentHash>( dtos.size() );
        for ( InstrumentDTO instrumentDTO : dtos ) {
            list.add( toEntity( instrumentDTO ) );
        }

        return list;
    }

    @Override
    public Map<String, InstrumentDTO> toDtoMaps(Map<String, InstrumentHash> entities) {
        if ( entities == null ) {
            return null;
        }

        Map<String, InstrumentDTO> map = new HashMap<String, InstrumentDTO>( Math.max( (int) ( entities.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, InstrumentHash> entry : entities.entrySet() ) {
            String key = entry.getKey();
            InstrumentDTO value = toDto( entry.getValue() );
            map.put( key, value );
        }

        return map;
    }

    @Override
    public Map<String, InstrumentHash> toEntityMaps(Map<String, InstrumentDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        Map<String, InstrumentHash> map = new HashMap<String, InstrumentHash>( Math.max( (int) ( dtos.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, InstrumentDTO> entry : dtos.entrySet() ) {
            String key = entry.getKey();
            InstrumentHash value = toEntity( entry.getValue() );
            map.put( key, value );
        }

        return map;
    }

    protected CandlestickDTO candlestickHashToCandlestickDTO(CandlestickHash candlestickHash) {
        if ( candlestickHash == null ) {
            return null;
        }

        CandlestickDTO candlestickDTO = new CandlestickDTO();

        candlestickDTO.setIsin( candlestickHash.getIsin() );
        if ( candlestickHash.getOpenTimestamp() != null ) {
            candlestickDTO.setOpenTimestamp( candlestickHash.getOpenTimestamp().toString() );
        }
        candlestickDTO.setOpenPrice( candlestickHash.getOpenPrice() );
        candlestickDTO.setHighPrice( candlestickHash.getHighPrice() );
        candlestickDTO.setLowPrice( candlestickHash.getLowPrice() );
        candlestickDTO.setClosingPrice( candlestickHash.getClosingPrice() );
        if ( candlestickHash.getCloseTimestamp() != null ) {
            candlestickDTO.setCloseTimestamp( candlestickHash.getCloseTimestamp().toString() );
        }

        return candlestickDTO;
    }

    protected Map<String, CandlestickDTO> stringCandlestickHashMapToStringCandlestickDTOMap(Map<String, CandlestickHash> map) {
        if ( map == null ) {
            return null;
        }

        Map<String, CandlestickDTO> map1 = new HashMap<String, CandlestickDTO>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, CandlestickHash> entry : map.entrySet() ) {
            String key = entry.getKey();
            CandlestickDTO value = candlestickHashToCandlestickDTO( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }

    protected CandlestickHash candlestickDTOToCandlestickHash(CandlestickDTO candlestickDTO) {
        if ( candlestickDTO == null ) {
            return null;
        }

        CandlestickHash candlestickHash = new CandlestickHash();

        if ( candlestickDTO.getOpenTimestamp() != null ) {
            candlestickHash.setOpenTimestamp( Instant.parse( candlestickDTO.getOpenTimestamp() ) );
        }
        candlestickHash.setOpenPrice( candlestickDTO.getOpenPrice() );
        candlestickHash.setHighPrice( candlestickDTO.getHighPrice() );
        candlestickHash.setLowPrice( candlestickDTO.getLowPrice() );
        candlestickHash.setClosingPrice( candlestickDTO.getClosingPrice() );
        if ( candlestickDTO.getCloseTimestamp() != null ) {
            candlestickHash.setCloseTimestamp( Instant.parse( candlestickDTO.getCloseTimestamp() ) );
        }
        candlestickHash.setIsin( candlestickDTO.getIsin() );

        return candlestickHash;
    }

    protected Map<String, CandlestickHash> stringCandlestickDTOMapToStringCandlestickHashMap(Map<String, CandlestickDTO> map) {
        if ( map == null ) {
            return null;
        }

        Map<String, CandlestickHash> map1 = new HashMap<String, CandlestickHash>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, CandlestickDTO> entry : map.entrySet() ) {
            String key = entry.getKey();
            CandlestickHash value = candlestickDTOToCandlestickHash( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }
}
