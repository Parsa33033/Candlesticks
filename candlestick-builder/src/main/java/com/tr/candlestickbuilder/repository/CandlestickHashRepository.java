package com.tr.candlestickbuilder.repository;

import com.tr.candlestickbuilder.model.redis.CandlestickHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandlestickHashRepository extends CrudRepository<CandlestickHash, String> {
}
