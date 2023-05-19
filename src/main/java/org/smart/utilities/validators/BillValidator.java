package org.smart.utilities.validators;

import static java.util.Objects.isNull;

import org.smart.utilities.dto.BillDTO;
import org.smart.utilities.exceptions.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class BillValidator implements Validator<BillDTO> {

  @Override
  public void validate(BillDTO dto) {
    if (isNull(dto.getBillType())) {
      exception("The bill needs to have a type");
    }

    if (isNull(dto.getUnits())) {
      exception("The bill needs to have units");
    }

    if (isNull(dto.getCostPerUnit())) {
      exception("The bill needs to have a cost per unit");
    }

  }

  private void exception(String message) {
    throw new BadRequestException(message);
  }
}
