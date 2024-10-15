package com.gsdd.keymanager.controllers;

import com.gsdd.keymanager.KeymanagerApplication;
import com.gsdd.keymanager.components.AccountLoginConverter;
import com.gsdd.keymanager.components.AccountLoginRequestConverter;
import com.gsdd.keymanager.entities.AccountLogin;
import com.gsdd.keymanager.requests.AccountLoginRequest;
import com.gsdd.keymanager.services.AccountLoginService;
import com.gsdd.keymanager.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = KeymanagerApplication.SECURITY_SCHEMA_ID)
@Tag(name = "AccountLogin CRUD")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login-accounts")
public class AccountLoginController {

  private static final String ACCOUNT_NOT_FOUND = "There is no match for the given account args.";
  private final AccountLoginConverter accountLoginConverter;
  private final AccountLoginRequestConverter accountLoginRequestConverter;
  private final AccountService accountService;
  private final AccountLoginService accountLoginService;

  private Optional<AccountLogin> findByLoginAndAccountId(String login, Long accountId) {
    return accountService.findByLogin(login)
        .map(accountLoginService::findByAccount)
        .orElseGet(Collections::emptyList)
        .stream()
        .filter(cxu -> Objects.equals(accountId, cxu.getId()))
        .findAny();
  }

  @Operation(summary = "Get some specific account using login and accountId.")
  @GetMapping("/{login}/{accountId}")
  public ResponseEntity<?> get(@PathVariable("login") String login,
      @PathVariable("accountId") Long accountId) {
    return findByLoginAndAccountId(login, accountId).map(accountLoginConverter::convert)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Get all the associated accounts using the login.")
  @GetMapping("/{login}")
  public ResponseEntity<?> getAllByUser(@PathVariable("login") String login) {
    return accountService.findByLogin(login)
        .map(accountLoginService::findByAccount)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Creates an account.")
  @PostMapping("/{login}")
  public ResponseEntity<?> save(@PathVariable("login") String login,
      @Valid @RequestBody AccountLoginRequest request, BindingResult bResultado) {
    if (bResultado.hasErrors()) {
      return ResponseEntity.badRequest().body(bResultado.getAllErrors());
    }
    if (accountService.findByLogin(login).isEmpty()) {
      return ResponseEntity.badRequest().body("Usuario no válido");
    }
    AccountLogin accountLogin =
        accountLoginService.save(accountLoginRequestConverter.convert(request));
    return Optional.ofNullable(accountLogin)
        .map(ResponseEntity.status(HttpStatus.CREATED)::body)
        .orElseGet(() -> ResponseEntity.internalServerError().build());
  }

  @Operation(summary = "Updates an account.")
  @PutMapping("/{login}/{accountId}")
  public ResponseEntity<?> update(@PathVariable("login") String login,
      @PathVariable("accountId") Long accountId, @Valid @RequestBody AccountLoginRequest request,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
    }
    Optional<AccountLogin> accountLoginOptional = findByLoginAndAccountId(login, accountId);
    if (accountLoginOptional.isPresent()) {
      AccountLogin accountLogin = accountLoginRequestConverter.convert(request);
      accountLogin.setId(accountId);
      accountLogin.setModificationDate(Date.from(Instant.now()));
      AccountLogin updatedAccount = accountLoginService.update(accountLogin);
      return Optional.ofNullable(updatedAccount)
          .map(ResponseEntity::ok)
          .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_MODIFIED).build());
    } else {
      return getNotFoundResponse();
    }
  }

  @Operation(summary = "Allows to delete an account.")
  @DeleteMapping("/{login}/{accountId}")
  public ResponseEntity<?> delete(@PathVariable("login") String login,
      @PathVariable("accountId") Long accountId) {
    return findByLoginAndAccountId(login, accountId).map((AccountLogin accountLogin) -> {
      accountLoginService.delete(accountLogin.getId());
      return ResponseEntity.noContent().build();
    }).orElseGet(this::getNotFoundResponse);
  }

  private ResponseEntity<Object> getNotFoundResponse() {
    return new ResponseEntity<>(ACCOUNT_NOT_FOUND, HttpStatus.NOT_FOUND);
  }
}
