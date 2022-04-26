package com.tr.candlestickprovider.repository;

import com.tr.candlestickprovider.model.redis.Instrument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepository extends CrudRepository<Instrument, String> {

}
