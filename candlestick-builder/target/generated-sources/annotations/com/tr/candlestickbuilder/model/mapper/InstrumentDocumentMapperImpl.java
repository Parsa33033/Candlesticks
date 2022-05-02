package com.tr.candlestickbuilder.model.mapper;

import com.tr.candlestickbuilder.model.dto.CandlestickDTO;
import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.mongodb.CandlestickDocument;
import com.tr.candlestickbuilder.model.mongodb.InstrumentDocument;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-02T19:52:10+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class InstrumentDocumentMapperImpl implements InstrumentDocumentMapper {

    @Override
    public InstrumentDTO toDto(InstrumentDocument entity) {
        if ( entity == null ) {
            return null;
        }

        InstrumentDTO instrumentDTO = new InstrumentDTO();

        instrumentDTO.setDescription( entity.getDescription() );
        instrumentDTO.setIsin( entity.getIsin() );
        instrumentDTO.setType( entity.getType() );
        instrumentDTO.setCandlesticks( stringCandlestickDocumentMapToStringCandlestickDTOMap( entity.getCandlesticks() ) );
        instrumentDTO.setTimestamp( entity.getTimestamp() );

        return instrumentDTO;
    }

    @Override
    public InstrumentDocument toEntity(InstrumentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        InstrumentDocument instrumentDocument = new InstrumentDocument();

        instrumentDocument.setDescription( dto.getDescription() );
        instrumentDocument.setType( dto.getType() );
        instrumentDocument.setTimestamp( dto.getTimestamp() );
        instrumentDocument.setIsin( dto.getIsin() );
        instrumentDocument.setCandlesticks( stringCandlestickDTOMapToStringCandlestickDocumentMap( dto.getCandlesticks() ) );

        return instrumentDocument;
    }

    @Override
    public List<InstrumentDTO> toDtoLists(List<InstrumentDocument> entities) {
        if ( entities == null ) {
            return null;
        }

        List<InstrumentDTO> list = new ArrayList<InstrumentDTO>( entities.size() );
        for ( InstrumentDocument instrumentDocument : entities ) {
            list.add( toDto( instrumentDocument ) );
        }

        return list;
    }

    @Override
    public List<InstrumentDocument> toEntityLists(List<InstrumentDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<InstrumentDocument> list = new ArrayList<InstrumentDocument>( dtos.size() );
        for ( InstrumentDTO instrumentDTO : dtos ) {
            list.add( toEntity( instrumentDTO ) );
        }

        return list;
    }

    @Override
    public Map<String, InstrumentDTO> toDtoMaps(Map<String, InstrumentDocument> entities) {
        if ( entities == null ) {
            return null;
        }

        Map<String, InstrumentDTO> map = new HashMap<String, InstrumentDTO>( Math.max( (int) ( entities.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, InstrumentDocument> entry : entities.entrySet() ) {
            String key = entry.getKey();
            InstrumentDTO value = toDto( entry.getValue() );
            map.put( key, value );
        }

        return map;
    }

    @Override
    public Map<String, InstrumentDocument> toEntityMaps(Map<String, InstrumentDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        Map<String, InstrumentDocument> map = new HashMap<String, InstrumentDocument>( Math.max( (int) ( dtos.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, InstrumentDTO> entry : dtos.entrySet() ) {
            String key = entry.getKey();
            InstrumentDocument value = toEntity( entry.getValue() );
            map.put( key, value );
        }

        return map;
    }

    protected CandlestickDTO candlestickDocumentToCandlestickDTO(CandlestickDocument candlestickDocument) {
        if ( candlestickDocument == null ) {
            return null;
        }

        CandlestickDTO candlestickDTO = new CandlestickDTO();

        candlestickDTO.setIsin( candlestickDocument.getIsin() );
        if ( candlestickDocument.getOpenTimestamp() != null ) {
            candlestickDTO.setOpenTimestamp( candlestickDocument.getOpenTimestamp().toString() );
        }
        candlestickDTO.setOpenPrice( candlestickDocument.getOpenPrice() );
        candlestickDTO.setHighPrice( candlestickDocument.getHighPrice() );
        candlestickDTO.setLowPrice( candlestickDocument.getLowPrice() );
        candlestickDTO.setClosingPrice( candlestickDocument.getClosingPrice() );
        if ( candlestickDocument.getCloseTimestamp() != null ) {
            candlestickDTO.setCloseTimestamp( candlestickDocument.getCloseTimestamp().toString() );
        }

        return candlestickDTO;
    }

    protected Map<String, CandlestickDTO> stringCandlestickDocumentMapToStringCandlestickDTOMap(Map<String, CandlestickDocument> map) {
        if ( map == null ) {
            return null;
        }

        Map<String, CandlestickDTO> map1 = new HashMap<String, CandlestickDTO>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, CandlestickDocument> entry : map.entrySet() ) {
            String key = entry.getKey();
            CandlestickDTO value = candlestickDocumentToCandlestickDTO( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }

    protected CandlestickDocument candlestickDTOToCandlestickDocument(CandlestickDTO candlestickDTO) {
        if ( candlestickDTO == null ) {
            return null;
        }

        CandlestickDocument candlestickDocument = new CandlestickDocument();

        if ( candlestickDTO.getOpenTimestamp() != null ) {
            candlestickDocument.setOpenTimestamp( Instant.parse( candlestickDTO.getOpenTimestamp() ) );
        }
        candlestickDocument.setOpenPrice( candlestickDTO.getOpenPrice() );
        candlestickDocument.setHighPrice( candlestickDTO.getHighPrice() );
        candlestickDocument.setLowPrice( candlestickDTO.getLowPrice() );
        candlestickDocument.setClosingPrice( candlestickDTO.getClosingPrice() );
        if ( candlestickDTO.getCloseTimestamp() != null ) {
            candlestickDocument.setCloseTimestamp( Instant.parse( candlestickDTO.getCloseTimestamp() ) );
        }
        candlestickDocument.setIsin( candlestickDTO.getIsin() );

        return candlestickDocument;
    }

    protected Map<String, CandlestickDocument> stringCandlestickDTOMapToStringCandlestickDocumentMap(Map<String, CandlestickDTO> map) {
        if ( map == null ) {
            return null;
        }

        Map<String, CandlestickDocument> map1 = new HashMap<String, CandlestickDocument>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, CandlestickDTO> entry : map.entrySet() ) {
            String key = entry.getKey();
            CandlestickDocument value = candlestickDTOToCandlestickDocument( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }
}
