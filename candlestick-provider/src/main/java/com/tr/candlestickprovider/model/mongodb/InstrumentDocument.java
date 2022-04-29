package com.tr.candlestickprovider.model.mongodb;

import com.tr.candlestickprovider.consts.Constant;
import com.tr.candlestickprovider.model.Instrument;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(Constant.INSTRUMENT)
public class InstrumentDocument extends Instrument {

    @Id
    private String isin;

    Map<String, CandlestickDocument> candlesticks;

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Map<String, CandlestickDocument> getCandlesticks() {
        return candlesticks;
    }

    public void setCandlesticks(Map<String, CandlestickDocument> candlesticks) {
        this.candlesticks = candlesticks;
    }

    @Override
    public String toString() {
        return "InstrumentDocument{" +
                "isin='" + isin + '\'' +
                ", candlesticks=" + candlesticks +
                '}';
    }
}
