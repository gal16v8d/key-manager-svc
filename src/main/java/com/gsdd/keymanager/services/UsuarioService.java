package com.gsdd.keymanager.services;

import com.gsdd.keymanager.entities.Usuario;
import com.gsdd.keymanager.repositories.UsuarioRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {

  private final UsuarioRepository usuarioRepository;

  public Optional<Usuario> findByUsername(String username) {
    return Optional.ofNullable(usuarioRepository.findByUsername(username));
  }

  public Optional<Usuario> findByUsernameAndPassword(String username, String password) {
    return Optional.ofNullable(usuarioRepository.findByUsernameAndPassword(username, password));
  }

  public Optional<Usuario> findByCodigoUsuario(Long codigoUsuario) {
    return usuarioRepository.findById(codigoUsuario);
  }

  public Usuario save(Usuario usuario) {
    return usuarioRepository.save(usuario);
  }

  public Usuario update(Usuario usuario) {
    return usuarioRepository.save(usuario);
  }

  public void delete(Long codigoUsuario) {
    usuarioRepository.deleteById(codigoUsuario);
  }
}
