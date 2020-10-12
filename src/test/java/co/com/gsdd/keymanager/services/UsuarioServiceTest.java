package co.com.gsdd.keymanager.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.com.gsdd.keymanager.entities.Usuario;
import co.com.gsdd.keymanager.repositories.UsuarioRepository;

@ExtendWith(SpringExtension.class)
public class UsuarioServiceTest {

	private static final String MOCK_USERNAME = "agalvis";
	private static final String MOCK_PWD = "123456";

	private UsuarioService usuarioService;
	@Mock
	private UsuarioRepository usuarioRepository;

	@BeforeEach
	public void setUp() {
		usuarioService = BDDMockito.spy(new UsuarioService(usuarioRepository));
	}

	@Test
	public void givenUserNotFoundThenfindByUsernameReturnTest() {
		BDDMockito.given(usuarioRepository.findByUsername(Mockito.anyString())).willReturn(null);
		Assertions.assertFalse(usuarioService.findByUsername(MOCK_USERNAME).isPresent());
	}

	@Test
	public void givenUserFoundThenfindByUsernameReturnTest() {
		BDDMockito.given(usuarioRepository.findByUsername(Mockito.anyString())).willReturn(Usuario.builder().build());
		Assertions.assertTrue(usuarioService.findByUsername(MOCK_USERNAME).isPresent());
	}

	@Test
	public void givenUserNotFoundThenfindByUsernameAndPasswordReturnTest() {
		BDDMockito.given(usuarioRepository.findByUsernameAndPassword(Mockito.anyString(), Mockito.anyString()))
				.willReturn(null);
		Assertions.assertFalse(usuarioService.findByUsernameAndPassword(MOCK_USERNAME, MOCK_PWD).isPresent());
	}

	@Test
	public void givenUserFoundThenfindByUsernameAndPasswordReturnTest() {
		BDDMockito.given(usuarioRepository.findByUsernameAndPassword(Mockito.anyString(), Mockito.anyString()))
				.willReturn(Usuario.builder().build());
		Assertions.assertTrue(usuarioService.findByUsernameAndPassword(MOCK_USERNAME, MOCK_PWD).isPresent());
	}

}
