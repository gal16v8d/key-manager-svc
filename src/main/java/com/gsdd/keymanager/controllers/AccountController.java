package com.gsdd.keymanager.controllers;

import com.gsdd.keymanager.KeymanagerApplication;
import com.gsdd.keymanager.components.AccountConverter;
import com.gsdd.keymanager.entities.Account;
import com.gsdd.keymanager.requests.AccountRequest;
import com.gsdd.keymanager.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Account CRUD")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

  private final AccountService accountService;
  private final AccountConverter accountConverter;

  private Optional<Account> findById(Long id) {
    return accountService.findById(id);
  }

  @Operation(summary = "Get user data using the login.")
  @GetMapping("/{login}")
  public ResponseEntity<Account> getByLogin(@PathVariable("login") String login) {
    return accountService.findByLogin(login)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Store user account data.")
  @PostMapping
  public ResponseEntity<?> save(@Valid @RequestBody AccountRequest request,
      BindingResult bResultado) {
    if (bResultado.hasErrors()) {
      return ResponseEntity.badRequest().body(bResultado.getAllErrors());
    }
    Account account = accountService.save(accountConverter.convert(request));
    return Optional.ofNullable(account)
        .map(ResponseEntity.status(HttpStatus.CREATED)::body)
        .orElseGet(() -> ResponseEntity.internalServerError().build());
  }

  @Operation(summary = "Update user account data.")
  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable("id") Long id,
      @Valid @RequestBody AccountRequest request, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
    }

    return findById(id).map((Account data) -> {
      Account account = accountConverter.convert(request);
      account.setAccountId(id);
      Account accountUpdated = accountService.update(account);
      return Optional.ofNullable(accountUpdated)
          .map(ResponseEntity::ok)
          .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_MODIFIED).build());
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Delete user account data.")
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
    return findById(id).map((Account a) -> {
      accountService.delete(id);
      return ResponseEntity.noContent().build();
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
