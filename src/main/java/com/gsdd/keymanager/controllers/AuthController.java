package com.gsdd.keymanager.controllers;

import com.gsdd.keymanager.components.JWTUtil;
import com.gsdd.keymanager.requests.AuthRequest;
import com.gsdd.keymanager.responses.AuthResponse;
import com.gsdd.keymanager.services.KMUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

@Tag(name = "Auth operations")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authManager;
  private final KMUserDetailsService userDetailsService;
  private final JWTUtil jwtUtil;

  @Operation(summary = "Permite autenticar a un usuario.")
  @GetMapping("/login")
  public ResponseEntity<?> basicAuth() {
    return ResponseEntity.status(HttpStatus.OK).body("Autenticado");
  }

  @Operation(summary = "Permite autenticar a un usuario.")
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
