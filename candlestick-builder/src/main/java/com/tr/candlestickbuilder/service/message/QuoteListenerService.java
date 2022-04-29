package com.tr.candlestickbuilder.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickbuilder.config.RabbitConfig;
import com.tr.candlestickbuilder.model.dto.CandlestickDTO;
import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.dto.QuoteDTO;
import com.tr.candlestickbuilder.model.redis.Candlestick;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class QuoteListenerService {
    private final static int CANDLESTICK_STACK_LIMIT = 30;

    Logger logger = LoggerFactory.getLogger(QuoteListenerService.class);

    private final CandlestickService candlestickService;

    private final InstrumentService instrumentService;

    private final ObjectMapper objectMapper;

    public QuoteListenerService(ObjectMapper objectMapper,
                                CandlestickService candlestickService,
                                InstrumentService instrumentService) {
        this.objectMapper = objectMapper;
        this.candlestickService = candlestickService;
        this.instrumentService = instrumentService;
    }

    /**
     * Listens to the quote_queue and gets the payload
     * converts the payload into a QuoteDTO object
     * Updates the candlestick and if is done, add it to the instruments
     * candlesticks list
     * @param payload
     * @throws JsonProcessingException
     */
    @RabbitListener(queues = {RabbitConfig.QUOTE_QUEUE})
    public void instrumentListener(String payload) throws JsonProcessingException {
        QuoteDTO quoteDTO = objectMapper.readValue(payload, QuoteDTO.class);
        logger.info("Quote ===> {}", quoteDTO);
        updateCandleStick(quoteDTO);
    }

    /**
     * Saves a new object of Candlestick
     * @param quoteDTO
     */
    public void saveCandlestick(QuoteDTO quoteDTO) {
        String isin = quoteDTO.getIsin();
        Instant quoteTimestamp = Instant.parse(quoteDTO.getTimestamp());
        CandlestickDTO candlestickDTO =
                new CandlestickDTO(isin,
                        quoteTimestamp,
                        quoteTimestamp,
                        quoteDTO.getPrice(),
                        quoteDTO.getPrice(),
                        quoteDTO.getPrice(),
                        quoteDTO.getPrice(),
                        quoteTimestamp);
        candlestickService.save(candlestickDTO);
    }

    /**
     * Pushes the created Candlestick to the instrument's candlesticks list
     * @param candlestickDTO
     */
    public void pushToInstrumentList(CandlestickDTO candlestickDTO) {
        String isin = candlestickDTO.getIsin();
        if (instrumentService.hasInstrument(isin)) {
            InstrumentDTO instrumentDTO = instrumentService.getByIsin(isin, CANDLESTICK_STACK_LIMIT);
            if (instrumentDTO.getCandlesticks() != null && !instrumentDTO.getCandlesticks().isEmpty()) {
                Instant candlestickOpenTimestamp = candlestickDTO.getOpenTimestamp();
                Instant candlestickOpenTimestampToMin = candlestickOpenTimestamp.truncatedTo(ChronoUnit.MINUTES);
                int i = 0;
                for (CandlestickDTO cs: instrumentDTO.getCandlesticks()) {
                    Instant csTimestampToMin = cs.getOpenTimestamp().truncatedTo(ChronoUnit.MINUTES);
                    if (candlestickOpenTimestampToMin.compareTo(csTimestampToMin) < 0) i++;
                    else break;
                }
                instrumentDTO.getCandlesticks().add(i, candlestickDTO);
                instrumentService.save(instrumentDTO);
            } else {
                List<CandlestickDTO> candlestickDTOS = new ArrayList<>();
                candlestickDTOS.add(candlestickDTO);
                instrumentDTO.setCandlesticks(candlestickDTOS);
                instrumentService.save(instrumentDTO);
            }
        } else {
            InstrumentDTO instrumentDTO = new InstrumentDTO();
            instrumentDTO.setIsin(isin);
            instrumentDTO.setDescription("");
            instrumentDTO.setCandlesticks(Arrays.asList(candlestickDTO));
            instrumentService.save(instrumentDTO);
        }
    }

    /**
     * Updates the candlesticks if minute is not passed
     * If minute is passed, the candlestick would be added
     * to the instrument's candlesticks list
     * @param quoteDTO
     */
    public void updateCandleStick(QuoteDTO quoteDTO) {
        String isin = quoteDTO.getIsin();
        if (candlestickService.hasCandleStick(isin)) {
            // If candlestick is present in the cache
            CandlestickDTO candlestickDTO = candlestickService.getCandleStickById(isin);
            Instant candlestickOpenTimestamp = candlestickDTO.getOpenTimestamp();
            Instant quoteTimestamp = Instant.parse(quoteDTO.getTimestamp());
            Instant candlestickOpenTimestampToMinute = candlestickOpenTimestamp.truncatedTo(ChronoUnit.MINUTES);
            Instant quoteTimestampToMinute = quoteTimestamp.truncatedTo(ChronoUnit.MINUTES);
            int compare = quoteTimestampToMinute.compareTo(candlestickOpenTimestampToMinute);
            if (compare == 0) {
                // If quote timestamp is equal to candlestick timestamp by minute
                if (quoteDTO.getPrice() > candlestickDTO.getHighPrice())
                    candlestickDTO.setHighPrice(quoteDTO.getPrice());
                if (quoteDTO.getPrice() < candlestickDTO.getLowPrice())
                    candlestickDTO.setLowPrice(quoteDTO.getPrice());
                candlestickDTO.setClosingPrice(quoteDTO.getPrice());
                candlestickService.save(candlestickDTO);
            } else if (compare > 0){
                // If quote timestamp is not equal to candlestick timestamp by minute
                candlestickDTO.setCloseTimestamp(quoteTimestamp);
                pushToInstrumentList(candlestickDTO);
                saveCandlestick(quoteDTO);
            }
        } else {
            // If candlestick is not present in cache, create one
            saveCandlestick(quoteDTO);
        }
    }
}