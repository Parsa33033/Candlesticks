package com.tr.candlestickbuilder.repository;

import com.tr.candlestickbuilder.model.redis.InstrumentHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentHashRepository extends CrudRepository<InstrumentHash, String> {

}
