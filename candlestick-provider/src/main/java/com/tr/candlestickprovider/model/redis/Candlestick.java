package com.tr.candlestickprovider.model.redis;

import com.tr.candlestickprovider.consts.Constant;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;

@RedisHash(Constant.CANDLESTICK)
public class Candlestick {

    @Id
    private String isin;

    Instant openTimestamp;

    Instant currentTimestamp;

    double openPrice;

    double highPrice;

    double lowPrice;

    double closingPrice;

    Instant closeTimestamp;

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Instant getOpenTimestamp() {
        return openTimestamp;
    }

    public void setOpenTimestamp(Instant openTimestamp) {
        this.openTimestamp = openTimestamp;
    }

    public Instant getCurrentTimestamp() {
        return currentTimestamp;
    }

    public void setCurrentTimestamp(Instant currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public double getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(double closingPrice) {
        this.closingPrice = closingPrice;
    }

    public Instant getCloseTimestamp() {
        return closeTimestamp;
    }

    public void setCloseTimestamp(Instant closeTimestamp) {
        this.closeTimestamp = closeTimestamp;
    }

    @Override
    public String toString() {
        return "Candlestick{" +
                "isin='" + isin + '\'' +
                ", openTimestamp=" + openTimestamp +
                ", currentTimestamp=" + currentTimestamp +
                ", openPrice=" + openPrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", closingPrice=" + closingPrice +
                ", closeTimestamp=" + closeTimestamp +
                '}';
    }
}
