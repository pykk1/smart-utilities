package org.smart.utilities.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ExpenseType {
  FOOD("Food"),
  TRANSPORTATION("Transportation"),
  ACCOMMODATION("Accommodation"),
  ENTERTAINMENT("Entertainment"),
  SHOPPING("Shopping"),
  HEALTHCARE("Healthcare"),
  EDUCATION("Education"),
  TRAVEL("Travel"),
  MAINTENANCE("Maintenance"),
  OTHER("Other");

  @JsonValue
  private final String label;

  ExpenseType(String label) {
    this.label = label;
  }

  public static ExpenseType fromString(String label) {
    for (ExpenseType expenseType : ExpenseType.values()) {
      if (expenseType.label.equalsIgnoreCase(label)) {
        return expenseType;
      }
    }
    throw new IllegalArgumentException("No expense type with label " + label + " found");
  }

  public static String[] labels() {
    return Arrays.stream(ExpenseType.values())
        .map(ExpenseType::getLabel)
        .toArray(String[]::new);
  }

}


