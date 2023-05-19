package org.smart.utilities.entity.converters;

import org.modelmapper.ModelMapper;
import org.smart.utilities.dto.ExpenseDTO;
import org.smart.utilities.entity.ExpenseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpenseConverter implements Converter<ExpenseDTO, ExpenseEntity> {

  private final ModelMapper modelMapper;

  @Autowired
  public ExpenseConverter() {
    this.modelMapper = new ModelMapper();
  }

  @Override
  public ExpenseEntity convertToEntity(ExpenseDTO expenseDTO) {

    return modelMapper.map(expenseDTO, ExpenseEntity.class);
  }

  @Override
  public ExpenseDTO convertToDTO(ExpenseEntity entity) {
    var dto = modelMapper.map(entity, ExpenseDTO.class);

    dto.setClientCode(entity.getUser().getClientCode());

    return dto;
  }
}
