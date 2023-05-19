package org.smart.utilities.entity.converters;

import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.smart.utilities.dto.ExpenseType;

@Converter(autoApply = true)
public class ExpenseTypeConverter implements AttributeConverter<ExpenseType, String> {

  @Override
  public String convertToDatabaseColumn(ExpenseType expenseType) {
    if (expenseType == null) {
      return null;
    }
    return expenseType.getLabel();
  }

  @Override
  public ExpenseType convertToEntityAttribute(String label) {
    if (label == null) {
      return null;
    }

    return Stream.of(ExpenseType.values())
        .filter(expenseType -> expenseType.getLabel().equals(label))
        .findFirst()
        .orElseThrow(IllegalAccessError::new);
  }
}

