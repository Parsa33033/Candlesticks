package com.tr.candlestickbuilder.repository;

import com.tr.candlestickbuilder.model.mongodb.InstrumentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentDocumentRepository extends MongoRepository<InstrumentDocument, String> {
}
