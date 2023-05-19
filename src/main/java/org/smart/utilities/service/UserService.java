package org.smart.utilities.service;

import org.smart.utilities.dto.UserDTO;
import org.smart.utilities.entity.UserEntity;
import org.smart.utilities.entity.converters.Converter;
import org.smart.utilities.repository.UserRepository;
import org.smart.utilities.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final Converter<UserDTO, UserEntity> converter;
  private final Validator<UserDTO> userValidator;

  @Autowired
  public UserService(UserRepository userRepository, Converter<UserDTO, UserEntity> converter,
      Validator<UserDTO> userValidator) {
    this.userRepository = userRepository;
    this.converter = converter;
    this.userValidator = userValidator;
  }

  public void registerUser(UserDTO userDTO) {
    userValidator.validate(userDTO);

    UserEntity user = converter.convertToEntity(userDTO);

    userRepository.save(user);
  }
}
