package com.gsdd.keymanager.repositories;

import com.gsdd.keymanager.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  Usuario findByUsername(String username);

  Usuario findByUsernameAndPassword(String username, String password);
}
