package com.gsdd.keymanager.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsdd.keymanager.components.AccountConverter;
import com.gsdd.keymanager.entities.Account;
import com.gsdd.keymanager.requests.AccountRequest;
import com.gsdd.keymanager.services.AccountService;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AccountControllerTest {

  private static final String URL_REQUEST = "/accounts";
  private static final String URL_REQUEST_ID = URL_REQUEST + "/{id}";
  private static final String MOCK_PWD = "123456";
  private static final String MOCK_USERNAME = "agalvis";
  private static final String MOCK_LAST_NAME = "Galvis";
  private static final String MOCK_FIRST_NAME = "Alex";
  private static final String PARAM_LOGIN = "login";
  private static final String MOCK_LOGIN = "";
  private static final long MOCK_CODE = 1L;
  @Autowired private WebApplicationContext context;
  @MockBean private AccountService accountService;
  @MockBean private AccountConverter accountConverter;
  private MockMvc mvc;

  @BeforeEach
  void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  void getByLoginTest() throws Exception {
    given(accountService.findByLogin(MOCK_LOGIN))
        .willReturn(Optional.ofNullable(Account.builder().build()));
    mvc.perform(
            get(URL_REQUEST)
                .param(PARAM_LOGIN, MOCK_LOGIN)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void getByLoginNotFoundTest() throws Exception {
    given(accountService.findByLogin(MOCK_LOGIN)).willReturn(Optional.empty());
    mvc.perform(
            get(URL_REQUEST)
                .param(PARAM_LOGIN, MOCK_LOGIN)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void getTest() throws Exception {
    willReturn(Optional.ofNullable(Account.builder().build())).given(accountService)
    .findById(MOCK_CODE);
    mvc.perform(
            get(URL_REQUEST_ID, MOCK_CODE)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void getNotFoundTest() throws Exception {
    given(accountService.findById(MOCK_CODE)).willReturn(Optional.empty());
    mvc.perform(
            get(URL_REQUEST_ID, MOCK_CODE)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  private AccountRequest createRequest() {
    return AccountRequest.builder()
        .firstName(MOCK_FIRST_NAME)
        .lastName(MOCK_LAST_NAME)
        .login(MOCK_USERNAME)
        .password(MOCK_PWD)
        .passwordAgain(MOCK_PWD)
        .build();
  }

  private String asJsonString(Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void updateTest() throws Exception {
    given(accountService.findById(MOCK_CODE))
        .willReturn(Optional.ofNullable(Account.builder().build()));
    Account account =
        Account.builder()
            .accountId(MOCK_CODE)
            .firstName(MOCK_FIRST_NAME)
            .lastName(MOCK_LAST_NAME)
            .build();
    willReturn(account)
        .given(accountConverter)
        .convert(any(AccountRequest.class));
    willReturn(account).given(accountService).update(any(Account.class));
    mvc.perform(
            put(URL_REQUEST_ID, MOCK_CODE)
                .content(
                    asJsonString(
                        createRequest()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", Matchers.notNullValue()))
        .andExpect(jsonPath("$.firstName", is(MOCK_FIRST_NAME)));
  }

  @Test
  void updateNotModifiedTest() throws Exception {
    given(accountService.findById(MOCK_CODE))
        .willReturn(Optional.ofNullable(Account.builder().build()));
    Account account = Account.builder().build();
    willReturn(account)
        .given(accountConverter)
        .convert(any(AccountRequest.class));
    willReturn(null).given(accountService).update(any(Account.class));
    mvc.perform(
            put(URL_REQUEST_ID, MOCK_CODE)
                .content(
                    asJsonString(
                        createRequest()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotModified());
  }

  @Test
  void updateNotFoundTest() throws Exception {
    given(accountService.findById(MOCK_CODE)).willReturn(Optional.empty());
    mvc.perform(
            put(URL_REQUEST_ID, MOCK_CODE)
                .content(
                    asJsonString(
                        createRequest()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteTest() throws Exception {
    given(accountService.findById(MOCK_CODE))
        .willReturn(Optional.ofNullable(Account.builder().build()));
    BDDMockito.doNothing().when(accountService).delete(MOCK_CODE);
    mvc.perform(
            delete(URL_REQUEST_ID, MOCK_CODE)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteNotFoundTest() throws Exception {
    given(accountService.findById(MOCK_CODE)).willReturn(Optional.empty());
    mvc.perform(
            delete(URL_REQUEST_ID, MOCK_CODE)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}
