package com.tr.candlestickprovider.repository;

import com.tr.candlestickprovider.model.mongodb.CandlestickDocument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandlestickDocumentRepository extends CrudRepository<CandlestickDocument, String> {
}
