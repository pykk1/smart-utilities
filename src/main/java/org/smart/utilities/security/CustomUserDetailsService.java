package org.smart.utilities.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.smart.utilities.dto.UserDTO;
import org.smart.utilities.entity.RoleEntity;
import org.smart.utilities.entity.UserEntity;
import org.smart.utilities.entity.converters.Converter;
import org.smart.utilities.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;
  private final Converter<UserDTO, UserEntity> userConverter;

  @Autowired
  public CustomUserDetailsService(UserRepository userRepository,
      Converter<UserDTO, UserEntity> userConverter) {
    this.userRepository = userRepository;
    this.userConverter = userConverter;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

    return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
  }

  private Collection<GrantedAuthority> mapRolesToAuthorities(List<RoleEntity> roles) {
    return roles.stream().map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName()))
        .collect(Collectors.toList());
  }

  public List<UserDTO> getAllCustomers() {
    return userRepository.findAllCustomers().stream()
        .map(userConverter::convertToDTO)
        .collect(Collectors.toList());
  }
}
