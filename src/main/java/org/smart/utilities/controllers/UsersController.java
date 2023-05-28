package org.smart.utilities.controllers;

import java.util.List;
import java.util.stream.Collectors;
import org.smart.utilities.dto.UserDTO;
import org.smart.utilities.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class UsersController {

  private final CustomUserDetailsService customUserDetailsService;

  @Autowired
  public UsersController(CustomUserDetailsService customUserDetailsService) {
    this.customUserDetailsService = customUserDetailsService;
  }

  @GetMapping("user")
  @ResponseStatus(HttpStatus.OK)
  public List<UserDTO> getAll() {
    return customUserDetailsService.getAllCustomers();
  }

  @GetMapping("client-codes")
  @ResponseStatus(HttpStatus.OK)
  public List<Integer> getAllClientCodes() {
    return customUserDetailsService.getAllCustomers().stream()
        .map(UserDTO::getClientCode)
        .collect(Collectors.toList());
  }
}
