package com.gsdd.keymanager.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.gsdd.keymanager.entities.CuentaXUsuario;
import com.gsdd.keymanager.entities.Usuario;
import com.gsdd.keymanager.repositories.CuentaXUsuarioRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CuentaXUsuarioService {

  private final CuentaXUsuarioRepository cuentaXUsuarioRepository;

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
