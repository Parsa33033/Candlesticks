package com.tr.candlestickprovider.repository;

import com.tr.candlestickprovider.model.redis.CandlestickHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandlestickHashRepository extends CrudRepository<CandlestickHash, String> {
}
