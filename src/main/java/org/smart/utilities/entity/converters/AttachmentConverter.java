package org.smart.utilities.entity.converters;

import org.modelmapper.ModelMapper;
import org.smart.utilities.dto.AttachmentDTO;
import org.smart.utilities.entity.AttachmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttachmentConverter implements Converter<AttachmentDTO, AttachmentEntity> {

  private final ModelMapper modelMapper;

  @Autowired
  public AttachmentConverter() {
    this.modelMapper = new ModelMapper();
  }

  @Override
  public AttachmentEntity convertToEntity(AttachmentDTO attachmentDTO) {
    return modelMapper.map(attachmentDTO, AttachmentEntity.class);
  }

  @Override
  public AttachmentDTO convertToDTO(AttachmentEntity entity) {
    return modelMapper.map(entity, AttachmentDTO.class);
  }
}
