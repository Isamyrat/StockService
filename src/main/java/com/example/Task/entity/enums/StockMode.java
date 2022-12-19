package com.example.Task.entity.enums;

public enum StockMode {
    ENABLED_MODE("ENABLE"),
    DISABLED_MODE("DISABLE");

    private final String str;

    StockMode(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
