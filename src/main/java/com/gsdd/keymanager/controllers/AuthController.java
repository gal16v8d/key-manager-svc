package com.gsdd.keymanager.controllers;

import com.gsdd.keymanager.exceptions.PasswordException;
import com.gsdd.keymanager.requests.AuthRequest;
import com.gsdd.keymanager.responses.AuthResponse;
import com.gsdd.keymanager.services.AccountService;
import com.gsdd.keymanager.utils.KmgrCipher;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth Controller")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AccountService accountService;

  @Value("${kmgr.http.apiKey}")
  private String secureKey;

  @Operation(summary = "Auth user in app.")
  @PostMapping("/login")
  public ResponseEntity<?> createToken(@RequestBody @Valid AuthRequest request,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
    }
    return accountService
        .findByLoginAndPassword(request.getLogin(), KmgrCipher.ENCODE.apply(request.getPassword()))
        .map(account -> ResponseEntity.ok(AuthResponse.builder().token(secureKey).build()))
        .orElseThrow(() -> new PasswordException("User or password incorrect"));
  }
}
