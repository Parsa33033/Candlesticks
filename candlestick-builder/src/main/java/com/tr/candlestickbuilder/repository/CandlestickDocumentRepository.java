package com.tr.candlestickbuilder.repository;

import com.tr.candlestickbuilder.model.CandlestickDocument;
import com.tr.candlestickbuilder.model.redis.Candlestick;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandlestickDocumentRepository extends MongoRepository<CandlestickDocument, String> {
}
