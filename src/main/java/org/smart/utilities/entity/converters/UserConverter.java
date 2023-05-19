package org.smart.utilities.entity.converters;

import java.util.Collections;
import java.util.Random;
import org.modelmapper.ModelMapper;
import org.smart.utilities.dto.CredentialsDTO;
import org.smart.utilities.dto.UserDTO;
import org.smart.utilities.entity.UserEntity;
import org.smart.utilities.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<UserDTO, UserEntity> {

  private final ModelMapper mapper;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  private final Integer INTEGER_LIMIT = 2147483647;

  public UserConverter(RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.mapper = new ModelMapper();
  }

  @Override
  public UserEntity convertToEntity(UserDTO dto) {
    var entity = mapper.map(dto, UserEntity.class);
    var role = roleRepository.findByName("USER").orElseThrow();
    var random = new Random();

    entity.setUsername(dto.getCredentials().getUsername());
    entity.setPassword(passwordEncoder.encode(dto.getCredentials().getPassword()));
    entity.setRoles(Collections.singletonList(role));
    entity.setClientCode(random.nextInt(INTEGER_LIMIT));
    entity.setBillingCode(random.nextInt(INTEGER_LIMIT));

    return entity;
  }

  @Override
  public UserDTO convertToDTO(UserEntity entity) {
    var userDTO = mapper.map(entity, UserDTO.class);
    var credentials = new CredentialsDTO();

    credentials.setUsername(entity.getUsername());
    userDTO.setCredentials(credentials);

    return userDTO;
  }
}
