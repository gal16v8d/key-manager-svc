package com.gsdd.keymanager.services;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

import com.gsdd.keymanager.entities.Account;
import com.gsdd.keymanager.repositories.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AccountServiceTest {

  private static final String MOCK_USERNAME = "agalvis";
  private static final String MOCK_PWD = "123456";

  private AccountService accountService;
  @Mock
  private AccountRepository accountRepository;

  @BeforeEach
  void setUp() {
    accountService = spy(new AccountService(accountRepository));
  }

  @Test
  void givenUserNotFoundThenFindByUsernameReturnTest() {
    given(accountRepository.findByLogin(anyString())).willReturn(null);
    Assertions.assertFalse(accountService.findByLogin(MOCK_USERNAME).isPresent());
  }

  @Test
  void givenUserFoundThenFindByUsernameReturnTest() {
    given(accountRepository.findByLogin(anyString())).willReturn(Account.builder().build());
    Assertions.assertTrue(accountService.findByLogin(MOCK_USERNAME).isPresent());
  }

  @Test
  void givenUserNotFoundThenFindByUsernameAndPasswordReturnTest() {
    given(accountRepository.findByLoginAndPassword(anyString(), anyString())).willReturn(null);
    Assertions
        .assertFalse(accountService.findByLoginAndPassword(MOCK_USERNAME, MOCK_PWD).isPresent());
  }

  @Test
  void givenUserFoundThenFindByUsernameAndPasswordReturnTest() {
    given(accountRepository.findByLoginAndPassword(anyString(), anyString()))
        .willReturn(Account.builder().build());
    Assertions
        .assertTrue(accountService.findByLoginAndPassword(MOCK_USERNAME, MOCK_PWD).isPresent());
  }
}
