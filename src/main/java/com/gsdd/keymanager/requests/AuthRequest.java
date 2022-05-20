package com.gsdd.keymanager.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Generated;

@Generated
@Data
@Schema(description = "Propiedades base para autenticarse en el app.")
public class AuthRequest {

  private String username;
  private String password;
}
