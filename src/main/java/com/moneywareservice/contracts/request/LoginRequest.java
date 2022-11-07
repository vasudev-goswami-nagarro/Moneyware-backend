package com.moneywareservice.contracts.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @NotNull
  private Boolean isRememberMe;
}
