package com.tr.candlestickbuilder.config;

import com.tr.candlestickbuilder.repository.CandlestickDocumentRepository;
import com.tr.candlestickbuilder.repository.InstrumentDocumentRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = {
        CandlestickDocumentRepository.class,
        InstrumentDocumentRepository.class
})
@Configuration
public class MongoDBConfig {
}
