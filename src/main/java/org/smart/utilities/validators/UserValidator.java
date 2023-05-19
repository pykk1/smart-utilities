package org.smart.utilities.validators;

import org.smart.utilities.dto.UserDTO;
import org.smart.utilities.exceptions.BadRequestException;
import org.smart.utilities.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator<UserDTO> {

  private final UserRepository userRepository;

  @Autowired
  public UserValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void validate(UserDTO user) {
    var credentials = user.getCredentials();

    if (userRepository.existsByUsernameOrEmail(credentials.getUsername(), user.getEmail())) {
      throw new BadRequestException("Username/email is taken");
    }
  }
}
