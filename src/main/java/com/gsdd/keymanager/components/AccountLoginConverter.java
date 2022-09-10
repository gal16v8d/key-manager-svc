package com.gsdd.keymanager.components;

import com.gsdd.keymanager.entities.AccountLogin;
import com.gsdd.keymanager.requests.AccountLoginRequest;
import com.gsdd.keymanager.utils.KmgrCypher;
import java.util.Optional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountLoginConverter implements Converter<AccountLogin, AccountLoginRequest> {

  @Override
  public AccountLoginRequest convert(AccountLogin source) {
    return Optional.ofNullable(source)
        .map(data -> AccountLoginRequest.builder().accountName(source.getAccountName())
            .url(source.getUrl()).login(source.getLogin())
            .password(KmgrCypher.decypher(source.getPassword()))
            .userLogin(data.getLogin()).build())
        .orElse(null);
  }
}
