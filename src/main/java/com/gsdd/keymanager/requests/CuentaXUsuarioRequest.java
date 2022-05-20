package com.gsdd.keymanager.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Generated;

/**
 * @author Great System Development Dynamic <GSDD> <br>
 *     Alexander Galvis Grisales <br>
 *     alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Generated
@Data
@Schema(description = "Propiedades base para crear una cuenta asociada a un usuario.")
public class CuentaXUsuarioRequest implements Serializable {

  private static final long serialVersionUID = 4085316621551574824L;

  @Schema(description = "Asociacion con el usuario.")
  @NotNull(message = "Debe tener un usuario asociado.")
  private String loginUsuario;

  @NotBlank(message = "El nombre de la cuenta es requerido.")
  private String nombreCuenta;

  @NotBlank(message = "El login es requerido.")
  private String username;

  @NotBlank(message = "La contraseña es requerida.")
  private String password;

  @Schema(description = "Url del servicio asociado a la cuenta.")
  private String url;
}
