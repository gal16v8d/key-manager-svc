package com.gsdd.keymanager.components;

import com.gsdd.keymanager.entities.Account;
import com.gsdd.keymanager.exceptions.PasswordException;
import com.gsdd.keymanager.requests.AccountRequest;
import com.gsdd.keymanager.utils.KmgrCypher;
import java.util.Objects;
import java.util.Optional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter implements Converter<AccountRequest, Account> {

  @Override
  public Account convert(AccountRequest source) {
    Optional<AccountRequest> requestOp = Optional.ofNullable(source);
    if (requestOp.isPresent()) {
      if (Objects.equals(source.getPassword(), source.getPasswordAgain())) {
        return Account.builder().firstName(source.getFirstName()).lastName(source.getLastName())
            .login(source.getLogin()).password(KmgrCypher.cypher(source.getPassword()))
            .build();
      }
      throw new PasswordException("Password doesn't match, please verify");
    }
    return null;
  }
}
