package org.smart.utilities.dto;

import lombok.Data;

@Data
public class AttachmentDTO {

  private String fileName;
  private String fileType;
  private byte[] data;

}
