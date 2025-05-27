package com.salon.enums;

public enum Role {
    ROLE_USER(0), ROLE_ADMIN(1);

    private final int value;

    private Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
