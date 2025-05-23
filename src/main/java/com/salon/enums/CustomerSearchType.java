package com.salon.enums;

public enum CustomerSearchType {
  ID(1), NAME(2), SURNAME(3), EMAIL(4), PHONE_NUMBER(5);

  private final int value;

  private CustomerSearchType(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

}
