package com.tr.candlestickprovider.repository;

import com.tr.candlestickprovider.model.CandlestickDocument;
import com.tr.candlestickprovider.model.redis.Candlestick;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandlestickDocumentRepository extends CrudRepository<CandlestickDocument, String> {
}
