package org.smart.utilities.dto;

import lombok.Data;

@Data
public class UserDTO {
  private Integer id;
  private String firstName;
  private String lastName;
  private CredentialsDTO credentials;
  private Integer clientCode;
  private Integer billingCode;
  private String address;
  private String email;
}