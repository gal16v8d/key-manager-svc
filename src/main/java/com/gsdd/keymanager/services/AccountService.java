package com.gsdd.keymanager.services;

import com.gsdd.keymanager.entities.Account;
import com.gsdd.keymanager.repositories.AccountRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {

  private final AccountRepository accountRepository;

  public Optional<Account> findByLogin(String login) {
    return Optional.ofNullable(accountRepository.findByLogin(login));
  }

  public Optional<Account> findByLoginAndPassword(String login, String password) {
    return Optional.ofNullable(accountRepository.findByLoginAndPassword(login, password));
  }

  public Optional<Account> findById(Long id) {
    return accountRepository.findById(id);
  }

  public Account save(Account account) {
    return accountRepository.save(account);
  }

  public Account update(Account account) {
    return accountRepository.save(account);
  }

  public void delete(Long id) {
    accountRepository.deleteById(id);
  }
}
