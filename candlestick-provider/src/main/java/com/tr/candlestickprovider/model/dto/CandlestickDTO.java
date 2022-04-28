package com.tr.candlestickprovider.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public class CandlestickDTO {

    @JsonIgnore
    private String isin;

    String openTimestamp;

    @JsonIgnore
    String currentTimestamp;

    double openPrice;

    double highPrice;

    double lowPrice;

    double closingPrice;

    String closeTimestamp;

    public CandlestickDTO(String isin,
                          String openTimestamp,
                          String currentTimestamp,
                          double openPrice,
                          double highPrice,
                          double lowPrice,
                          double closingPrice,
                          String closeTimestamp) {
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

    public String getOpenTimestamp() {
        return openTimestamp;
    }

    public void setOpenTimestamp(String openTimestamp) {
        this.openTimestamp = openTimestamp;
    }

    public String getCurrentTimestamp() {
        return currentTimestamp;
    }

    public void setCurrentTimestamp(String currentTimestamp) {
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

    public String getCloseTimestamp() {
        return closeTimestamp;
    }

    public void setCloseTimestamp(String closeTimestamp) {
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
