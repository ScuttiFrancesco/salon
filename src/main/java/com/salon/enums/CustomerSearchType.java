package com.salon.enums;

public enum CustomerSearchType {
  ID(0), NAME(1), SURNAME(2), EMAIL(3), PHONE_NUMBER(4);

  private final int value;

  private CustomerSearchType(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

}
