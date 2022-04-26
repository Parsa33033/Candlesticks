package com.tr.candlestickprovider.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public class CandlestickDTO {

    @JsonIgnore
    private String isin;

    Instant openTimestamp;

    @JsonIgnore
    Instant currentTimestamp;

    double openPrice;

    double highPrice;

    double lowPrice;

    double closingPrice;

    Instant closeTimestamp;

    public CandlestickDTO(String isin,
                          Instant openTimestamp,
                          Instant currentTimestamp,
                          double openPrice,
                          double highPrice,
                          double lowPrice,
                          double closingPrice,
                          Instant closeTimestamp) {
        this.isin = isin;
        this.openTimestamp = openTimestamp;
        this.currentTimestamp = currentTimestamp;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closingPrice = closingPrice;
        this.closeTimestamp = closeTimestamp;
    }

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
        return "CandlestickDTO{" +
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
