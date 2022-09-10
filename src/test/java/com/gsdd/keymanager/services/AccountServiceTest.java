package com.gsdd.keymanager.services;

import com.gsdd.keymanager.entities.Account;
import com.gsdd.keymanager.repositories.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AccountServiceTest {

  private static final String MOCK_USERNAME = "agalvis";
  private static final String MOCK_PWD = "123456";

  private AccountService accountService;
  @Mock private AccountRepository accountRepository;

  @BeforeEach
  void setUp() {
    accountService = BDDMockito.spy(new AccountService(accountRepository));
  }

  @Test
  void givenUserNotFoundThenfindByUsernameReturnTest() {
    BDDMockito.given(accountRepository.findByLogin(Mockito.anyString())).willReturn(null);
    Assertions.assertFalse(accountService.findByLogin(MOCK_USERNAME).isPresent());
  }

  @Test
  void givenUserFoundThenfindByUsernameReturnTest() {
    BDDMockito.given(accountRepository.findByLogin(Mockito.anyString()))
        .willReturn(Account.builder().build());
    Assertions.assertTrue(accountService.findByLogin(MOCK_USERNAME).isPresent());
  }

  @Test
  void givenUserNotFoundThenfindByUsernameAndPasswordReturnTest() {
    BDDMockito.given(
        accountRepository.findByLoginAndPassword(Mockito.anyString(), Mockito.anyString()))
        .willReturn(null);
    Assertions.assertFalse(
        accountService.findByLoginAndPassword(MOCK_USERNAME, MOCK_PWD).isPresent());
  }

  @Test
  void givenUserFoundThenfindByUsernameAndPasswordReturnTest() {
    BDDMockito.given(
        accountRepository.findByLoginAndPassword(Mockito.anyString(), Mockito.anyString()))
        .willReturn(Account.builder().build());
    Assertions.assertTrue(
        accountService.findByLoginAndPassword(MOCK_USERNAME, MOCK_PWD).isPresent());
  }
}
