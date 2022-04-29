package com.tr.candlestickbuilder.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickbuilder.config.RabbitConfig;
import com.tr.candlestickbuilder.model.dto.CandlestickDTO;
import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.dto.QuoteDTO;
import com.tr.candlestickbuilder.model.enums.Type;
import com.tr.candlestickbuilder.service.CandlestickService;
import com.tr.candlestickbuilder.service.InstrumentService;
import com.tr.candlestickbuilder.service.exceptions.CandlesticksNullException;
import com.tr.candlestickbuilder.service.exceptions.QuoteNotHandledWhenReceivedException;
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
     *
     * @param payload
     * @throws JsonProcessingException
     */
    @RabbitListener(queues = {RabbitConfig.QUOTE_QUEUE}, concurrency = "200")
    public void instrumentListener(String payload) throws JsonProcessingException {
        QuoteDTO quoteDTO = objectMapper.readValue(payload, QuoteDTO.class);
        logger.info("Quote ===> {}", quoteDTO);
        updateCandleStick(quoteDTO);
    }

    public void updateCandleStick(QuoteDTO quoteDTO) {
        try {
            String isin = quoteDTO.getIsin();
            // check if instrument exists if not create
            if (instrumentService.hasInstrument(isin)) {
                // create a key as quote timestamp truncated to minute (open timestamp)
                String key = truncateTimestampToMin(quoteDTO.getTimestamp());
                InstrumentDTO instrumentDTO = instrumentService.getByIsin(isin, 0);
                Map<String, CandlestickDTO> candlestickDTOMap = instrumentDTO.getCandlesticks();
                if (candlestickDTOMap == null) throw new CandlesticksNullException();
                // fetch candlestick if key exists (opentimestamp as key)
                if (candlestickDTOMap.containsKey(key)) {
                    // assume quote timestamp as T and quote price as P
                    String quoteTimestamp = quoteDTO.getTimestamp();
                    Instant T = Instant.parse(quoteTimestamp);
                    double P = quoteDTO.getPrice();

                    CandlestickDTO candlestick = candlestickDTOMap.get(key);
                    // if T < opentimestamp update opentimestamp to T
                    if (T.isBefore(Instant.parse(candlestick.getOpenTimestamp()))) {
                        candlestick.setOpenTimestamp(T.toString());
                        candlestick.setOpenPrice(P);
                    }
                    // if T > closeTimestamp update closetimestamp to T
                    if (T.isAfter(Instant.parse(candlestick.getCloseTimestamp()))) {
                        candlestick.setCloseTimestamp(T.toString());
                        candlestick.setClosingPrice(P);
                    }
                    // if P < lowprice update minprice to P
                    if (P < candlestick.getLowPrice()) {
                        candlestick.setLowPrice(P);
                    }
                    // if P > maxprice update maxprice to P
                    if (P > candlestick.getHighPrice()) {
                        candlestick.setHighPrice(P);
                    }
                    instrumentDTO.getCandlesticks().put(key, candlestick);
                    instrumentService.save(instrumentDTO);
                } else {
                /*
                  create a new candle stick and put it in the map of instruments candlesticks with
                    as timestamp truncated to minute
                 */
                    CandlestickDTO candlestickDTO = new CandlestickDTO(
                            isin,
                            quoteDTO.getTimestamp(),
                            quoteDTO.getPrice(),
                            quoteDTO.getPrice(),
                            quoteDTO.getPrice(),
                            quoteDTO.getPrice(),
                            quoteDTO.getTimestamp()
                    );
                    instrumentDTO.getCandlesticks()
                            .put(truncateTimestampToMin(quoteDTO.getTimestamp()), candlestickDTO);
                    instrumentService.save(instrumentDTO);
                }
            } else {
                // create a new instrument
                InstrumentDTO newInstrumentDTO = new InstrumentDTO();
                newInstrumentDTO.setIsin(isin);
                newInstrumentDTO.setType(Type.ADD);
                newInstrumentDTO.setTimestamp(Instant.now().toString());
                newInstrumentDTO.setDescription("");
                newInstrumentDTO.setCandlesticks(new HashMap<>());
                instrumentService.save(newInstrumentDTO);
            }
        } catch (CandlesticksNullException e) {
            throw new CandlesticksNullException();
        } catch (Exception e) {
            throw new QuoteNotHandledWhenReceivedException(quoteDTO.toString(), e.getMessage());
        }
    }

    public String truncateTimestampToMin(String timestamp) {
        return Instant.parse(timestamp).truncatedTo(ChronoUnit.MINUTES).toString();
    }
}