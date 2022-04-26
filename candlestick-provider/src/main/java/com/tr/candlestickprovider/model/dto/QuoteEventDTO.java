package com.tr.candlestickprovider.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tr.candlestickprovider.model.enums.Type;

public class QuoteEventDTO {
    private Type type;

    @JsonProperty("data")
    private QuoteDTO quoteDTO;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public QuoteDTO getQuoteDTO() {
        return quoteDTO;
    }

    public void setQuoteDTO(QuoteDTO quoteDTO) {
        this.quoteDTO = quoteDTO;
    }

    @Override
    public String toString() {
        return "QuoteEventDTO{" +
                "type=" + type +
                ", quoteDTO=" + quoteDTO +
                '}';
    }
}
