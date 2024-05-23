package com.example.application.views.numericfield;

import java.math.BigDecimal;

public class DataBean {
    private String numberAsString = "";
    private BigDecimal numberAsBigDecimal = new BigDecimal(124454);

    public DataBean() {
    }

    public String getNumberAsString() {
        return numberAsString;
    }

    public void setNumberAsString(String numberAsString) {
        this.numberAsString = numberAsString;
    }

    public BigDecimal getNumberAsBigDecimal() {
        return numberAsBigDecimal;
    }

    public void setNumberAsBigDecimal(BigDecimal numberAsBigDecimal) {
        this.numberAsBigDecimal = numberAsBigDecimal;
    }
}
