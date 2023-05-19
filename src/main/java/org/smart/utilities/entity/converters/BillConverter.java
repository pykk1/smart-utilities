package org.smart.utilities.entity.converters;

import org.modelmapper.ModelMapper;
import org.smart.utilities.dto.BillDTO;
import org.smart.utilities.entity.BillEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillConverter implements Converter<BillDTO, BillEntity> {

  private final ModelMapper modelMapper;

  @Autowired
  public BillConverter() {
    this.modelMapper = new ModelMapper();
  }

  @Override
  public BillEntity convertToEntity(BillDTO billDTO) {

    return modelMapper.map(billDTO, BillEntity.class);
  }

  @Override
  public BillDTO convertToDTO(BillEntity entity) {
    var dto = modelMapper.map(entity, BillDTO.class);

    dto.setClientCode(entity.getUser().getClientCode());

    return dto;
  }
}
