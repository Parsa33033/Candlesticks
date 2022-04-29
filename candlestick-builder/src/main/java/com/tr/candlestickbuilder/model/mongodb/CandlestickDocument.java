package com.tr.candlestickbuilder.model.mongodb;

import com.tr.candlestickbuilder.consts.Constant;
import com.tr.candlestickbuilder.model.Candlestick;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
