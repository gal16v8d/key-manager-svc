package com.gsdd.keymanager.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
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
@Schema(description = "Propiedades base para crear un usuario.")
public class UsuarioRequest implements Serializable {

  private static final long serialVersionUID = 4543485518965788215L;
  private String primerNombre;
  private String primerApellido;
  private String username;
  private String password;
  private String passwordAgain;
}
