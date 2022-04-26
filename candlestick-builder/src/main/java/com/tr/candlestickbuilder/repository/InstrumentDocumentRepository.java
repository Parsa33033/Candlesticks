package com.tr.candlestickbuilder.repository;

import com.tr.candlestickbuilder.model.InstrumentDocument;
import com.tr.candlestickbuilder.model.redis.Instrument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentDocumentRepository extends MongoRepository<InstrumentDocument, String> {
}
