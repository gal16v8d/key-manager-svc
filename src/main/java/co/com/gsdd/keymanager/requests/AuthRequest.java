package co.com.gsdd.keymanager.requests;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Generated;

@Generated
@Data
@ApiModel(description = "Propiedades base para autenticarse en el app.")
public class AuthRequest {

	private String username;
	private String password;
}
