package co.com.gsdd.keymanager.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.keymanager.components.JWTUtil;
import co.com.gsdd.keymanager.requests.AuthRequest;
import co.com.gsdd.keymanager.responses.AuthResponse;
import co.com.gsdd.keymanager.services.KMUserDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Api("Auth")
@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authManager;
	private final KMUserDetailsService userDetailsService;
	private final JWTUtil jwtUtil;

	@ApiOperation(value = "Permite autenticar a un usuario.")
	@GetMapping("/login")
	public ResponseEntity<?> basicAuth() {
		return ResponseEntity.status(HttpStatus.OK).body("Autenticado");
	}

	@ApiOperation(value = "Permite autenticar a un usuario.")
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> createToken(@RequestBody AuthRequest request) {
		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
			String jwt = jwtUtil.generateToken(userDetails);
			return ResponseEntity.ok(new AuthResponse(jwt));
		} catch (BadCredentialsException e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
}
