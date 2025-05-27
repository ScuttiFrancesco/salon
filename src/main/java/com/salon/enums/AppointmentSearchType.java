package com.salon.enums;

public enum AppointmentSearchType {
    ID(0), DATE(1), CUSTOMER_NAME(2), OPERATOR_NAME(3), DURATION(4);

    private final int value;

    private AppointmentSearchType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
