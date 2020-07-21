package co.com.gsdd.keymanager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.gsdd.keymanager.entities.CuentaXUsuario;
import co.com.gsdd.keymanager.entities.Usuario;
import co.com.gsdd.keymanager.repositories.CuentaXUsuarioRepository;

@Service
public class CuentaXUsuarioService {

    private final CuentaXUsuarioRepository cuentaXUsuarioRepository;

    @Autowired
    public CuentaXUsuarioService(CuentaXUsuarioRepository cuentaXUsuarioRepository) {
        this.cuentaXUsuarioRepository = cuentaXUsuarioRepository;
    }

    public Optional<CuentaXUsuario> findById(Long codigoCuenta) {
        return cuentaXUsuarioRepository.findById(codigoCuenta);
    }

    public List<CuentaXUsuario> findByUsuario(Usuario usuario) {
        return cuentaXUsuarioRepository.findByUsuario(usuario);
    }

    public CuentaXUsuario save(CuentaXUsuario cuentaXUsuario) {
        return cuentaXUsuarioRepository.save(cuentaXUsuario);
    }

    public CuentaXUsuario update(CuentaXUsuario cuentaXUsuario) {
        return cuentaXUsuarioRepository.save(cuentaXUsuario);
    }

    public void delete(Long codigoCuenta) {
        cuentaXUsuarioRepository.deleteById(codigoCuenta);
    }
}
