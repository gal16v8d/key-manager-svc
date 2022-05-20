package com.gsdd.keymanager.repositories;

import com.gsdd.keymanager.entities.CuentaXUsuario;
import com.gsdd.keymanager.entities.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaXUsuarioRepository extends JpaRepository<CuentaXUsuario, Long> {

  List<CuentaXUsuario> findByUsuario(Usuario usuario);
}
