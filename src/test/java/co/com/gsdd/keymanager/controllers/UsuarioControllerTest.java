package co.com.gsdd.keymanager.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.gsdd.keymanager.components.UsuarioRequestConverter;
import co.com.gsdd.keymanager.entities.Usuario;
import co.com.gsdd.keymanager.requests.UsuarioRequest;
import co.com.gsdd.keymanager.services.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UsuarioControllerTest {

	private static final String URL_REQUEST = "/usuarios/{codigoUsuario}";
	private static final String MOCK_PWD = "123456";
	private static final String MOCK_USERNAME = "agalvis";
	private static final String MOCK_LAST_NAME = "Galvis";
	private static final String MOCK_FIRST_NAME = "Alex";
	private static final String PARAM_LOGIN = "login";
	private static final String MOCK_LOGIN = "";
	private static final long MOCK_CODE = 1L;
	@Autowired
	private WebApplicationContext context;
	@MockBean
	private UsuarioService usuarioService;
	@MockBean
	private UsuarioRequestConverter usuarioRequestConverter;
	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void getByLoginTest() throws Exception {
		BDDMockito.given(usuarioService.findByUsername(MOCK_LOGIN))
				.willReturn(Optional.ofNullable(Usuario.builder().build()));
		mvc.perform(MockMvcRequestBuilders.get("/usuarios").param(PARAM_LOGIN, MOCK_LOGIN)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void getByLoginNotFoundTest() throws Exception {
		BDDMockito.given(usuarioService.findByUsername(MOCK_LOGIN)).willReturn(Optional.empty());
		mvc.perform(MockMvcRequestBuilders.get("/usuarios").param(PARAM_LOGIN, MOCK_LOGIN)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	public void getTest() throws Exception {
		BDDMockito.given(usuarioService.findByCodigoUsuario(MOCK_CODE))
				.willReturn(Optional.ofNullable(Usuario.builder().build()));
		mvc.perform(MockMvcRequestBuilders.get(URL_REQUEST, MOCK_CODE).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void getNotFoundTest() throws Exception {
		BDDMockito.given(usuarioService.findByCodigoUsuario(MOCK_CODE)).willReturn(Optional.empty());
		mvc.perform(MockMvcRequestBuilders.get(URL_REQUEST, MOCK_CODE).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	private UsuarioRequest createUsuarioRequest(String primerNombre, String primerApellido, String username,
			String password, String passAgain) {
		UsuarioRequest request = new UsuarioRequest();
		request.setPrimerNombre(primerNombre);
		request.setPrimerApellido(primerApellido);
		request.setUsername(username);
		request.setPassword(password);
		request.setPasswordAgain(passAgain);
		return request;
	}

	private String asJsonString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void updateTest() throws Exception {
		BDDMockito.given(usuarioService.findByCodigoUsuario(MOCK_CODE))
				.willReturn(Optional.ofNullable(Usuario.builder().build()));
		Usuario user = Usuario.builder().codigoUsuario(MOCK_CODE).primerNombre(MOCK_FIRST_NAME)
				.primerApellido(MOCK_LAST_NAME).build();
		BDDMockito.doReturn(user).when(usuarioRequestConverter).convert(BDDMockito.any(UsuarioRequest.class));
		BDDMockito.doReturn(user).when(usuarioService).update(BDDMockito.any(Usuario.class));
		mvc.perform(MockMvcRequestBuilders.put(URL_REQUEST, MOCK_CODE)
				.content(asJsonString(
						createUsuarioRequest(MOCK_FIRST_NAME, MOCK_LAST_NAME, MOCK_USERNAME, MOCK_PWD, MOCK_PWD)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.primerNombre", Matchers.notNullValue()))
				.andExpect(jsonPath("$.primerNombre", is(MOCK_FIRST_NAME)));
	}

	@Test
	public void updateNotModifiedTest() throws Exception {
		BDDMockito.given(usuarioService.findByCodigoUsuario(MOCK_CODE))
				.willReturn(Optional.ofNullable(Usuario.builder().build()));
		Usuario user = Usuario.builder().build();
		BDDMockito.doReturn(user).when(usuarioRequestConverter).convert(BDDMockito.any(UsuarioRequest.class));
		BDDMockito.doReturn(null).when(usuarioService).update(BDDMockito.any(Usuario.class));
		mvc.perform(MockMvcRequestBuilders.put(URL_REQUEST, MOCK_CODE)
				.content(asJsonString(
						createUsuarioRequest(MOCK_FIRST_NAME, MOCK_LAST_NAME, MOCK_USERNAME, MOCK_PWD, MOCK_PWD)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotModified());
	}

	@Test
	public void updateNotFoundTest() throws Exception {
		BDDMockito.given(usuarioService.findByCodigoUsuario(MOCK_CODE)).willReturn(Optional.empty());
		mvc.perform(MockMvcRequestBuilders.put(URL_REQUEST, MOCK_CODE)
				.content(asJsonString(
						createUsuarioRequest(MOCK_FIRST_NAME, MOCK_LAST_NAME, MOCK_USERNAME, MOCK_PWD, MOCK_PWD)))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	public void deleteTest() throws Exception {
		BDDMockito.given(usuarioService.findByCodigoUsuario(MOCK_CODE))
				.willReturn(Optional.ofNullable(Usuario.builder().build()));
		BDDMockito.doNothing().when(usuarioService).delete(MOCK_CODE);
		mvc.perform(MockMvcRequestBuilders.delete(URL_REQUEST, MOCK_CODE).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	public void deleteNotFoundTest() throws Exception {
		BDDMockito.given(usuarioService.findByCodigoUsuario(MOCK_CODE)).willReturn(Optional.empty());
		mvc.perform(MockMvcRequestBuilders.delete(URL_REQUEST, MOCK_CODE).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
