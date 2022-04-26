package com.tr.candlestickbuilder.repository;

import com.tr.candlestickbuilder.model.redis.Candlestick;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandlestickRepository extends CrudRepository<Candlestick, String> {
}
