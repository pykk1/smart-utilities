package org.smart.utilities.dto;

import lombok.Getter;

@Getter
public enum BillType {
  WATER("Water"),
  ELECTRICITY("Electricity"),
  GAS("Gas"),
  SANITATION("Sanitation"),
  RENT("Rent"),
  INTERNET("Internet"),
  PHONE("Phone"),
  OTHER("Other");


  private final String label;

  BillType(String label) {
    this.label = label;
  }

  public static BillType fromString(String label) {
    for (BillType billType : BillType.values()) {
      if (billType.label.equalsIgnoreCase(label)) {
        return billType;
      }
    }
    throw new IllegalArgumentException("No bill type with label " + label + " found");
  }

}


