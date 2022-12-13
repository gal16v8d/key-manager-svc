package com.gsdd.keymanager.repository;

import com.gsdd.keymanager.entities.Account;
import com.gsdd.keymanager.repositories.AccountRepository;
import javax.persistence.PersistenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class AccountRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private AccountRepository accountRepository;

  @Test
  void ifFirstNombreIsLongThenFailTest() {
    Account account = Account.builder()
        .firstName("Abcdefghijklmnopq")
        .lastName("test")
        .accountId(1L)
        .password("test")
        .login("agalvis")
        .role(null)
        .build();
    accountRepository.save(account);
    Assertions.assertThrows(PersistenceException.class, () -> entityManager.flush());
  }
}
