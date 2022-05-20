package com.gsdd.keymanager.repository;

import com.gsdd.keymanager.entities.Usuario;
import com.gsdd.keymanager.repositories.UsuarioRepository;
import javax.persistence.PersistenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class UsuarioRepositoryTest {

  @Autowired private TestEntityManager entityManager;
  @Autowired private UsuarioRepository usuarioRepository;

  @Test
  void ifPrimerNombreIsLongThenFailTest() {
    Usuario user =
        Usuario.builder()
            .primerNombre("Abcdefghijklmnopq")
            .primerApellido("test")
            .codigoUsuario(1L)
            .password("test")
            .username("agalvis")
            .rol(null)
            .build();
    usuarioRepository.save(user);
    Assertions.assertThrows(PersistenceException.class, () -> entityManager.flush());
  }
}
