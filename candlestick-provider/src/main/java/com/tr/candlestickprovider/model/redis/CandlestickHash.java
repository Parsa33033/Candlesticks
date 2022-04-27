package com.tr.candlestickprovider.model.redis;

import com.tr.candlestickprovider.consts.Constant;
import com.tr.candlestickprovider.model.Candlestick;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;

@RedisHash(Constant.CANDLESTICK)
public class CandlestickHash extends Candlestick {
    @Id
    private String isin;

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    @Override
    public String toString() {
        return "CandlestickHash{" +
                "isin='" + isin + '\'' +
                "} " + super.toString();
    }
}
