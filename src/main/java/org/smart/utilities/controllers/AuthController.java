package org.smart.utilities.controllers;

import java.util.Collections;
import org.smart.utilities.dto.AuthResponseDTO;
import org.smart.utilities.dto.CredentialsDTO;
import org.smart.utilities.entity.RoleEntity;
import org.smart.utilities.entity.UserEntity;
import org.smart.utilities.repository.RoleRepository;
import org.smart.utilities.repository.UserRepository;
import org.smart.utilities.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JWTGenerator jwtGenerator;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
      RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtGenerator = jwtGenerator;
  }

  @PostMapping("login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody CredentialsDTO credentialsDTO) {
    var authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(credentialsDTO.getUsername(),
            credentialsDTO.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtGenerator.generateToken(authentication);
    return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
  }

  @PostMapping("register")
  public ResponseEntity<String> register(@RequestBody CredentialsDTO credentialsDTO) {
    if (userRepository.existsByUsername(credentialsDTO.getUsername())) {
      return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
    }

    UserEntity user = new UserEntity();
    user.setUsername(credentialsDTO.getUsername());
    user.setPassword(passwordEncoder.encode(credentialsDTO.getPassword()));

    RoleEntity role = roleRepository.findByName("USER").get();
    user.setRoles(Collections.singletonList(role));

    userRepository.save(user);

    return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
  }
}
