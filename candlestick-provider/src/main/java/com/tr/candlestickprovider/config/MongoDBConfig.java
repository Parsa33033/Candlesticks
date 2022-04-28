package com.tr.candlestickprovider.config;

import com.tr.candlestickprovider.repository.CandlestickDocumentRepository;
import com.tr.candlestickprovider.repository.InstrumentDocumentRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = {
        CandlestickDocumentRepository.class,
        InstrumentDocumentRepository.class
})
@Configuration
public class MongoDBConfig {
}
