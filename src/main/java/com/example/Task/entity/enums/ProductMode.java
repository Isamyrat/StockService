package com.example.Task.entity.enums;

public enum ProductMode {
    MANUAL_MODE("MANUAL_MODE"),
    AUTO_MODE("AUTO_MODE");

    private final String str;

    ProductMode(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
