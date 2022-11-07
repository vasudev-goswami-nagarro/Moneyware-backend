package com.moneywareservice.web;


import com.moneywareservice.GenericResponse;
import com.moneywareservice.contracts.response.JwtResponse;
import com.moneywareservice.contracts.request.LoginRequest;
import com.moneywareservice.security.jwt.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthResource {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  TokenProvider tokenProvider;

  @Operation(summary = "Authenticate user credentials")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content),
      @ApiResponse(responseCode = "200", description = "Return token",
          content = @Content) })
  @PostMapping("/signin")
  public ResponseEntity<Object> authenticateUser(@Valid @RequestBody
                                                   LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenProvider.createToken(authentication, loginRequest.getIsRememberMe());

    JwtResponse jwtResponse = new JwtResponse(jwt);
    GenericResponse<Object> genericResponse = new GenericResponse<>(jwtResponse);
    return new ResponseEntity<>(genericResponse, HttpStatus.OK);
  }
}
