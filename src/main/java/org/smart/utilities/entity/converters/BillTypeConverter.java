package org.smart.utilities.entity.converters;

import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.smart.utilities.dto.BillType;

@Converter(autoApply = true)
public class BillTypeConverter implements AttributeConverter<BillType, String> {

  @Override
  public String convertToDatabaseColumn(BillType billType) {
    if (billType == null) {
      return null;
    }
    return billType.getLabel();
  }

  @Override
  public BillType convertToEntityAttribute(String label) {
    if (label == null) {
      return null;
    }

    return Stream.of(BillType.values())
        .filter(billType -> billType.getLabel().equals(label))
        .findFirst()
        .orElseThrow(IllegalAccessError::new);
  }
}
