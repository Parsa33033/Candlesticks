package com.tr.candlestickprovider.repository;

import com.tr.candlestickprovider.model.mongodb.CandlestickDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandlestickDocumentRepository extends MongoRepository<CandlestickDocument, String> {
}
