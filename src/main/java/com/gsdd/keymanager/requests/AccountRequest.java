package com.gsdd.keymanager.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
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
@Schema(description = "Basic properties to create an account.")
public class AccountRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 4543485518965788215L;
  private String firstName;
  private String lastName;
  private String login;
  private String password;
  private String passwordAgain;
}
