package org.smart.utilities.controllers;

import org.smart.utilities.dto.AuthResponseDTO;
import org.smart.utilities.dto.CredentialsDTO;
import org.smart.utilities.dto.UserDTO;
import org.smart.utilities.repository.RoleRepository;
import org.smart.utilities.repository.UserRepository;
import org.smart.utilities.security.JWTGenerator;
import org.smart.utilities.service.UserService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JWTGenerator jwtGenerator;
  private final UserService userService;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
      RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator,
      UserService userService) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtGenerator = jwtGenerator;
    this.userService = userService;
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
  @ResponseStatus(HttpStatus.CREATED)
  public void register(@RequestBody UserDTO userDTO) {
    userService.registerUser(userDTO);
  }
}
