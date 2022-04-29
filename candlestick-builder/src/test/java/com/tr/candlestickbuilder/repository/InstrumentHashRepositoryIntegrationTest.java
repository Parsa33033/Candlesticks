package com.tr.candlestickbuilder.repository;

import com.tr.candlestickbuilder.model.enums.Type;
import com.tr.candlestickbuilder.model.redis.InstrumentHash;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import redis.embedded.RedisServer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataRedisTest
class InstrumentHashRepositoryIntegrationTest {
    String isin = "isin";

    Type type = Type.ADD;

    String description = "description";

    InstrumentHash instrumentHash;

    @Autowired
    InstrumentHashRepository instrumentHashRepository;

    RedisServer redisServer;

    @BeforeEach
    public void setUp() {
        redisServer = RedisServer.builder().port(6379).build();
        redisServer.start();
        instrumentHash = new InstrumentHash();
        instrumentHash.setIsin(isin);
        instrumentHash.setType(type);
        instrumentHash.setDescription(description);
    }

    @Test
    public void assertThatRepositoryWorks() {
        InstrumentHash i1 = instrumentHashRepository.save(instrumentHash);
        Optional<InstrumentHash> oi2 = instrumentHashRepository.findById(isin);
        assertThat(oi2.isPresent()).isEqualTo(true);
        InstrumentHash i2 = oi2.get();
        assertThat(i2.toString()).isEqualTo(i1.toString());
        instrumentHashRepository.deleteById(isin);
        oi2 = instrumentHashRepository.findById(isin);
        assertThat(oi2.isPresent()).isEqualTo(false);
    }

    @AfterEach
    public void closeUp() {
        redisServer.stop();
    }
}