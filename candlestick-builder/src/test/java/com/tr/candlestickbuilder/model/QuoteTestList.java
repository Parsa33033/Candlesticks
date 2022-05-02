package com.tr.candlestickbuilder.model;

import java.util.List;

public class QuoteTestList {
    List<QuoteTest> quoteTestList;

    public List<QuoteTest> getQuoteTestList() {
        return quoteTestList;
    }

    public void setQuoteTestList(List<QuoteTest> quoteTestList) {
        this.quoteTestList = quoteTestList;
    }

    @Override
    public String toString() {
        return "QuoteTestList{" +
                "quoteTestList=" + quoteTestList +
                '}';
    }
}
