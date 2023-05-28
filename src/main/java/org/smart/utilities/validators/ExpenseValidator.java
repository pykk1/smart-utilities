package org.smart.utilities.validators;

import static java.util.Objects.isNull;

import org.smart.utilities.dto.ExpenseDTO;
import org.smart.utilities.exceptions.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ExpenseValidator implements Validator<ExpenseDTO> {

  @Override
  public void validate(ExpenseDTO dto) {
    if (isNull(dto.getExpenseType())) {
      exception("The expense needs to have a type");
    }

  }

  private void exception(String message) {
    throw new BadRequestException(message);
  }
}
