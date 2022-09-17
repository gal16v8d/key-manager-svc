package com.gsdd.keymanager.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gsdd.keymanager.entities.Account;
import com.gsdd.keymanager.services.AccountService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

  private static final String API_PATH = "/auth/login";
  private static final String MOCK_AUTH_BODY =
      """
      {
        "login": "actor",
        "password": "L123$"
      }
      """;
  @Autowired private MockMvc mvc;
  @MockBean private AccountService accountService;

  @Test
  void testCreateToken() throws Exception {
    willReturn(Optional.ofNullable(Account.builder().build()))
        .given(accountService)
        .findByLoginAndPassword(any(), any());
    mvc.perform(post(API_PATH).contentType(MediaType.APPLICATION_JSON).content(MOCK_AUTH_BODY))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").exists());
  }
}
