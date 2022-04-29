package com.tr.candlestickbuilder.model.redis;

import com.tr.candlestickbuilder.consts.Constant;
import com.tr.candlestickbuilder.model.Candlestick;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

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
