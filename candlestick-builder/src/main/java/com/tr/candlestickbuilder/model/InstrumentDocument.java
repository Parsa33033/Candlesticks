package com.tr.candlestickbuilder.model;

import com.tr.candlestickbuilder.consts.Constant;
import com.tr.candlestickbuilder.model.enums.Type;
import com.tr.candlestickbuilder.model.redis.Candlestick;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Document(Constant.INSTRUMENT)
public class InstrumentDocument {

    @Id
    private String isin;

    private String description;

    private Type type;

    private List<CandlestickDocument> candlesticks;


    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<CandlestickDocument> getCandlesticks() {
        return candlesticks;
    }

    public void setCandlesticks(List<CandlestickDocument> candlesticks) {
        this.candlesticks = candlesticks;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "isin='" + isin + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", candlesticks=" + candlesticks +
                '}';
    }
}
