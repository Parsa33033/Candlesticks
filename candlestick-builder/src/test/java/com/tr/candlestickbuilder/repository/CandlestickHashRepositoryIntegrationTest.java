package com.tr.candlestickbuilder.repository;

import com.tr.candlestickbuilder.model.redis.CandlestickHash;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import redis.embedded.RedisServer;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataRedisTest
class CandlestickHashRepositoryIntegrationTest {

    String isin = "isin";

    double openPrice = 1.0;

    double highPrice = 1.0;

    double lowPrice = 1.0;

    double closingPrice = 1.0;

    CandlestickHash candlestickHash;

    @Autowired
    CandlestickHashRepository candlestickHashRepository;

    RedisServer redisServer;

    @BeforeEach
    public void setUp() {
        redisServer = RedisServer.builder().port(6379).build();
        redisServer.start();
        Instant instant = Instant.now();
        candlestickHash = new CandlestickHash();
        candlestickHash.setIsin(isin);
        candlestickHash.setCloseTimestamp(instant);
        candlestickHash.setClosingPrice(closingPrice);
        candlestickHash.setCurrentTimestamp(instant);
        candlestickHash.setHighPrice(highPrice);
        candlestickHash.setLowPrice(lowPrice);
        candlestickHash.setOpenPrice(openPrice);
        candlestickHash.setOpenTimestamp(instant);
    }


    @Test
    public void assertThatRepositoryWorks() {
        CandlestickHash c1 = candlestickHashRepository.save(candlestickHash);
        Optional<CandlestickHash> oc2 = candlestickHashRepository.findById(isin);
        CandlestickHash c2 = oc2.get();
        c1.setOpenTimestamp(c1.getOpenTimestamp().truncatedTo(ChronoUnit.SECONDS));
        c2.setOpenTimestamp(c2.getOpenTimestamp().truncatedTo(ChronoUnit.SECONDS));
        c1.setCloseTimestamp(c1.getCloseTimestamp().truncatedTo(ChronoUnit.SECONDS));
        c2.setCloseTimestamp(c2.getCloseTimestamp().truncatedTo(ChronoUnit.SECONDS));
        c1.setCurrentTimestamp(c1.getCurrentTimestamp().truncatedTo(ChronoUnit.SECONDS));
        c2.setCurrentTimestamp(c2.getCurrentTimestamp().truncatedTo(ChronoUnit.SECONDS));
        assertThat(oc2.isPresent()).isEqualTo(true);
        assertThat(c2.toString()).isEqualTo(c1.toString());
        long size = candlestickHashRepository.findAll().spliterator().getExactSizeIfKnown();
        assertThat(size).isGreaterThan(0);
        candlestickHashRepository.deleteById(isin);
        oc2 = candlestickHashRepository.findById(isin);
        assertThat(oc2.isPresent()).isEqualTo(false);
    }

    @AfterEach
    public void closeUp() {
        redisServer.stop();
    }
}