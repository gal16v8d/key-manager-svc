package com.gsdd.keymanager.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

/**
 * @author Great System Development Dynamic <GSDD> <br> Alexander Galvis
 *         Grisales <br> alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Generated
@Builder
@Data
@Schema(description = "Base properties to create an account related to an user.")
public class AccountLoginRequest implements Serializable {

  private static final long serialVersionUID = 4085316621551574824L;

  @Schema(description = "Account association.")
  @NotNull(message = "Should be associated with an user.")
  private String userLogin;

  @NotBlank(message = "Account name is required.")
  private String accountName;

  @NotBlank(message = "Login is required.")
  private String login;

  @NotBlank(message = "Password is required.")
  private String password;

  @Schema(description = "Account reference url.")
  private String url;
}
