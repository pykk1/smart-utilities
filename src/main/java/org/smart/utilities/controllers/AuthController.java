package org.smart.utilities.controllers;

import org.smart.utilities.dto.AuthResponseDTO;
import org.smart.utilities.dto.CredentialsDTO;
import org.smart.utilities.dto.UserDTO;
import org.smart.utilities.security.JWTGenerator;
import org.smart.utilities.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JWTGenerator jwtGenerator;
  private final UserService userService;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager, JWTGenerator jwtGenerator,
      UserService userService) {
    this.authenticationManager = authenticationManager;
    this.jwtGenerator = jwtGenerator;
    this.userService = userService;
  }

  @PostMapping("/auth/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody CredentialsDTO credentialsDTO) {
    var authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(credentialsDTO.getUsername(),
            credentialsDTO.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtGenerator.generateToken(authentication);
    return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
  }

  @PostMapping("/admin/register")
  @ResponseStatus(HttpStatus.CREATED)
  public void register(@RequestBody UserDTO userDTO) {
    userService.registerUser(userDTO);
  }
}
