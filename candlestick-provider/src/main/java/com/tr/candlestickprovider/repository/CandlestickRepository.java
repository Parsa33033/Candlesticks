package com.tr.candlestickprovider.repository;

import com.tr.candlestickprovider.model.redis.Candlestick;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandlestickRepository extends CrudRepository<Candlestick, String> {
}
