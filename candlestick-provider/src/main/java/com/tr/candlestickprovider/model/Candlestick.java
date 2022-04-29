package com.tr.candlestickprovider.model;

import java.time.Instant;

public class Candlestick {

    Instant openTimestamp;

    double openPrice;

    double highPrice;

    double lowPrice;

    double closingPrice;

    Instant closeTimestamp;

    public Instant getOpenTimestamp() {
        return openTimestamp;
    }

    public void setOpenTimestamp(Instant openTimestamp) {
        this.openTimestamp = openTimestamp;
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
                "openTimestamp=" + openTimestamp +
                ", openPrice=" + openPrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", closingPrice=" + closingPrice +
                ", closeTimestamp=" + closeTimestamp +
                '}';
    }
}
