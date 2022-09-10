package com.gsdd.keymanager.services;

import com.gsdd.keymanager.entities.Account;
import com.gsdd.keymanager.entities.AccountLogin;
import com.gsdd.keymanager.repositories.AccountLoginRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountLoginService {

  private final AccountLoginRepository accountLoginRepository;

  public Optional<AccountLogin> findById(Long id) {
    return accountLoginRepository.findById(id);
  }

  public List<AccountLogin> findByAccount(Account account) {
    return accountLoginRepository.findByAccount(account);
  }

  public AccountLogin save(AccountLogin accountLogin) {
    return accountLoginRepository.save(accountLogin);
  }

  public AccountLogin update(AccountLogin accountLogin) {
    return accountLoginRepository.save(accountLogin);
  }

  public void delete(Long id) {
    accountLoginRepository.deleteById(id);
  }
}
