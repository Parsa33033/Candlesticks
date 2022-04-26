package com.tr.candlestickbuilder.model.dto;

public class QuoteDTO {

    private double price;

    private String isin;

    private String timestamp;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return "QuoteDTO{" +
                "price=" + price +
                ", isin='" + isin + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
