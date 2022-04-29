package com.tr.candlestickbuilder.model.redis;

import com.tr.candlestickbuilder.consts.Constant;
import com.tr.candlestickbuilder.model.Instrument;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;
import java.util.Map;

@RedisHash(Constant.INSTRUMENT)
public class InstrumentHash extends Instrument {

    @Id
    private String isin;

    private Map<String, CandlestickHash> candlesticks;

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Map<String, CandlestickHash> getCandlesticks() {
        return candlesticks;
    }

    public void setCandlesticks(Map<String, CandlestickHash> candlesticks) {
        this.candlesticks = candlesticks;
    }

    @Override
    public String toString() {
        return "InstrumentHash{" +
                "isin='" + isin + '\'' +
                ", candlesticks=" + candlesticks +
                "} " + super.toString();
    }
}
