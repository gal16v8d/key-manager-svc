package com.gsdd.keymanager.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

@Generated
@Builder
@Data
@Schema(description = "Required props to login in app.")
public class AuthRequest {

  @NotBlank(message = "Login is required.")
  private String login;
  @NotBlank(message = "Password is required.")
  private String password;
}
