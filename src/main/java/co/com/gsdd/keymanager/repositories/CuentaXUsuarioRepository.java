package co.com.gsdd.keymanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.gsdd.keymanager.entities.CuentaXUsuario;
import co.com.gsdd.keymanager.entities.Usuario;

@Repository
public interface CuentaXUsuarioRepository extends JpaRepository<CuentaXUsuario, Long> {

    List<CuentaXUsuario> findByUsuario(Usuario usuario);

}
