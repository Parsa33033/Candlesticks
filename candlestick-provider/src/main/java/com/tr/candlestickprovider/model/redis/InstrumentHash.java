package com.tr.candlestickprovider.model.redis;

import com.tr.candlestickprovider.consts.Constant;
import com.tr.candlestickprovider.model.Instrument;
import com.tr.candlestickprovider.model.enums.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.util.List;

@RedisHash(Constant.INSTRUMENT)
public class InstrumentHash extends Instrument {

    @Id
    private String isin;

    private List<CandlestickHash> candlesticks;

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public List<CandlestickHash> getCandlesticks() {
        return candlesticks;
    }

    public void setCandlesticks(List<CandlestickHash> candlesticks) {
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