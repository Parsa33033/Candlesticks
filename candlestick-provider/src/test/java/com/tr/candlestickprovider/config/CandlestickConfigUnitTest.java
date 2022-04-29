package com.tr.candlestickprovider.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.MongoRepositoryConfigurationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(CandlestickConfig.class)
@SpringBootTest(classes = CandlestickConfig.class)
class CandlestickConfigUnitTest {
    Logger logger = LoggerFactory.getLogger(CandlestickConfigUnitTest.class);

    @Autowired
    CandlestickConfig candlestickConfig;

    @Test
    public void testICandleStickConfigurationGetsPartnerURLFromApplicationYML() {
        String url = candlestickConfig.getPartner().getUrl();
        logger.info("Partner url: {}", url);
        assertThat(url).isNotNull().isNotEmpty();
    }
}