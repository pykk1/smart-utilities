package org.smart.utilities.entity.converters;

import org.modelmapper.ModelMapper;
import org.smart.utilities.dto.AttachmentDTO;
import org.smart.utilities.dto.ExpenseDTO;
import org.smart.utilities.entity.AttachmentEntity;
import org.smart.utilities.entity.ExpenseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpenseConverter implements Converter<ExpenseDTO, ExpenseEntity> {

  private final ModelMapper modelMapper;

  @Autowired
  public ExpenseConverter() {
    this.modelMapper = new ModelMapper();
    configureModelMapper();
  }

  private void configureModelMapper() {
    modelMapper.typeMap(AttachmentEntity.class, AttachmentDTO.class)
        .addMappings(mapper -> mapper.skip(AttachmentDTO::setData));
    modelMapper.typeMap(AttachmentDTO.class, AttachmentEntity.class)
        .addMappings(mapper -> mapper.map(AttachmentDTO::getData, AttachmentEntity::setData));
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
