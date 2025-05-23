package com.salon.enums;

public enum CustomerSearchDirection {
    ASC(1), DESC(2);

    private final int value;

    private CustomerSearchDirection(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
