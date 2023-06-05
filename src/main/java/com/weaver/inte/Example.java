package com.weaver.inte;


import java.math.BigDecimal;

public class Example {
    private String name;
    private BigDecimal amount;

    public Example() {
    }

    public Example(String name, BigDecimal amount) {
        this.name = name;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Example(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
