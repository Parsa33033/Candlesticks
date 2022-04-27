package com.tr.candlestickprovider.repository;

import com.tr.candlestickprovider.model.redis.InstrumentHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentHashRepository extends CrudRepository<InstrumentHash, String> {

}
