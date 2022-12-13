package com.gsdd.keymanager.components;

import com.gsdd.keymanager.entities.AccountLogin;
import com.gsdd.keymanager.requests.AccountLoginRequest;
import com.gsdd.keymanager.services.AccountService;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountLoginRequestConverter implements Converter<AccountLoginRequest, AccountLogin> {

  private final AccountService accountService;

  @Override
  public AccountLogin convert(AccountLoginRequest source) {
    return Optional.ofNullable(source)
        .map(
            data -> accountService.findByLogin(source.getUserLogin())
                .map(
                    account -> AccountLogin.builder()
                        .account(account)
                        .accountName(source.getAccountName())
                        .url(source.getUrl())
                        .login(account.getLogin())
                        .password(account.getPassword())
                        .modificationDate(Date.from(Instant.now()))
                        .build())
                .orElse(null))
        .orElse(null);
  }
}
