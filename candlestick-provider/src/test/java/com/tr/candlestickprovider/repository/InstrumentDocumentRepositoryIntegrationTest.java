package com.tr.candlestickprovider.repository;

import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.model.mongodb.InstrumentDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class InstrumentDocumentRepositoryIntegrationTest {
    String isin = "isin";

    Type type = Type.ADD;

    String description = "description";

    InstrumentDocument instrumentDocument;

    @Autowired
    InstrumentDocumentRepository instrumentDocumentRepository;

    @BeforeEach
    public void setUp() {
        instrumentDocument = new InstrumentDocument();
        instrumentDocument.setIsin(isin);
        instrumentDocument.setType(type);
        instrumentDocument.setDescription(description);
    }

    @Test
    public void assertThatRepositoryWorks() {
        InstrumentDocument i1 = instrumentDocumentRepository.save(instrumentDocument);
        Optional<InstrumentDocument> oi2 = instrumentDocumentRepository.findById(isin);
        assertThat(oi2.isPresent()).isEqualTo(true);
        InstrumentDocument i2 = oi2.get();
        assertThat(i2.toString()).isEqualTo(i1.toString());
        instrumentDocumentRepository.deleteById(isin);
        oi2 = instrumentDocumentRepository.findById(isin);
        assertThat(oi2.isPresent()).isEqualTo(false);
    }
}