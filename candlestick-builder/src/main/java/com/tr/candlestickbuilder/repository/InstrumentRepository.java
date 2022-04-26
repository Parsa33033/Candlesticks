package com.tr.candlestickbuilder.repository;

import com.tr.candlestickbuilder.model.redis.Instrument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepository extends CrudRepository<Instrument, String> {

}
