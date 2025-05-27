package com.salon.enums;

public enum AppointmentSearchDirection {
    ASC(0), DESC(1);

    private final int value;

    private AppointmentSearchDirection(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
