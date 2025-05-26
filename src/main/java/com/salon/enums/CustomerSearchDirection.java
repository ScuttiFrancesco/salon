package com.salon.enums;

public enum CustomerSearchDirection {
    ASC(0), DESC(1);

    private final int value;

    private CustomerSearchDirection(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
