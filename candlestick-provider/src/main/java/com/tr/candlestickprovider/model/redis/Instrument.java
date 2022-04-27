package com.tr.candlestickprovider.model.redis;

import com.tr.candlestickprovider.consts.Constant;
import com.tr.candlestickprovider.model.enums.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.util.List;

@RedisHash(Constant.INSTRUMENT)
public class Instrument {

    @Id
    private String isin;

    private String description;

    private Type type;

    private List<Candlestick> candlesticks;


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

    public List<Candlestick> getCandlesticks() {
        return candlesticks;
    }

    public void setCandlesticks(List<Candlestick> candlesticks) {
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
