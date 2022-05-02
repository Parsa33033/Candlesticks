package com.tr.candlestickbuilder.model;

import com.tr.candlestickbuilder.model.dto.QuoteDTO;
import com.tr.candlestickbuilder.model.enums.ResultType;

public class QuoteTest {
    QuoteDTO quoteDTO;

    ResultType resultType;

    public QuoteDTO getQuoteDTO() {
        return quoteDTO;
    }

    public void setQuoteDTO(QuoteDTO quoteDTO) {
        this.quoteDTO = quoteDTO;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        return "QuoteStream{" +
                "quoteDTO=" + quoteDTO +
                ", resultType=" + resultType +
                '}';
    }
}
