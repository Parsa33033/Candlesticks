package com.tr.candlestickprovider.repository;

import com.tr.candlestickprovider.model.mongodb.InstrumentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentDocumentRepository extends MongoRepository<InstrumentDocument, String> {
}
