package com.tr.candlestickprovider.model.mongodb;

import com.tr.candlestickprovider.consts.Constant;
import com.tr.candlestickprovider.model.Candlestick;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(Constant.CANDLESTICK)
public class CandlestickDocument extends Candlestick {

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
        return "CandlestickDocument{" +
                "isin='" + isin + '\'' +
                "} " + super.toString();
    }
}
