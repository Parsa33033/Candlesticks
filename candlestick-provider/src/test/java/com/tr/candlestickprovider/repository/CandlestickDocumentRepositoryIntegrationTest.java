package com.tr.candlestickprovider.repository;

import com.mongodb.client.MongoClient;
import com.tr.candlestickprovider.config.ApplicationConfig;
import com.tr.candlestickprovider.model.Candlestick;
import com.tr.candlestickprovider.model.mongodb.CandlestickDocument;
import groovy.util.logging.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataMongoTest
@ActiveProfiles("test")
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class CandlestickDocumentRepositoryIntegrationTest {

    String isin = "isin";

    double openPrice = 1.0;

    double highPrice = 1.0;

    double lowPrice = 1.0;

    double closingPrice = 1.0;

    CandlestickDocument candlestickDocument;

    @Autowired
    CandlestickDocumentRepository candlestickDocumentRepository;

    @BeforeEach
    public void setUp() {
        Instant instant = Instant.now();
        candlestickDocument = new CandlestickDocument();
        candlestickDocument.setIsin(isin);
        candlestickDocument.setCloseTimestamp(instant);
        candlestickDocument.setClosingPrice(closingPrice);
        candlestickDocument.setCurrentTimestamp(instant);
        candlestickDocument.setHighPrice(highPrice);
        candlestickDocument.setLowPrice(lowPrice);
        candlestickDocument.setOpenPrice(openPrice);
        candlestickDocument.setOpenTimestamp(instant);
    }


    @Test
    public void assertThatRepositoryWorks() {
        CandlestickDocument c1 = candlestickDocumentRepository.save(candlestickDocument);
        Optional<CandlestickDocument> oc2 = candlestickDocumentRepository.findById(isin);
        CandlestickDocument c2 = oc2.get();
        c1.setOpenTimestamp(c1.getOpenTimestamp().truncatedTo(ChronoUnit.SECONDS));
        c2.setOpenTimestamp(c2.getOpenTimestamp().truncatedTo(ChronoUnit.SECONDS));
        c1.setCloseTimestamp(c1.getCloseTimestamp().truncatedTo(ChronoUnit.SECONDS));
        c2.setCloseTimestamp(c2.getCloseTimestamp().truncatedTo(ChronoUnit.SECONDS));
        c1.setCurrentTimestamp(c1.getCurrentTimestamp().truncatedTo(ChronoUnit.SECONDS));
        c2.setCurrentTimestamp(c2.getCurrentTimestamp().truncatedTo(ChronoUnit.SECONDS));
        assertThat(oc2.isPresent()).isEqualTo(true);
        assertThat(c2.toString()).isEqualTo(c1.toString());
        long size = candlestickDocumentRepository.findAll().spliterator().getExactSizeIfKnown();
        assertThat(size).isGreaterThan(0);
        candlestickDocumentRepository.deleteById(isin);
        oc2 = candlestickDocumentRepository.findById(isin);
        assertThat(oc2.isPresent()).isEqualTo(false);
    }
}