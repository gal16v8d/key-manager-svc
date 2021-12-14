package com.gsdd.keymanager.services;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gsdd.keymanager.entities.Usuario;
import com.gsdd.keymanager.exceptions.ContrasenaException;
import com.gsdd.keymanager.repositories.UsuarioRepository;
import com.gsdd.keymanager.utils.CifradoKeyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class KMUserDetailsService implements UserDetailsService {

  private static final String USUARIO_NO_VALIDO = "Usuario no válido";

  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Usuario> usuarioOp = Optional.ofNullable(usuarioRepository.findByUsername(username));
    log.info("Encontrado {}", usuarioOp.isPresent());
    return usuarioOp.map(usuario -> new User(usuario.getUsername(),
        passwordEncoder.encode(CifradoKeyManager.descifrarKM(usuario.getPassword())),
        new ArrayList<>())).orElseThrow(() -> new ContrasenaException(USUARIO_NO_VALIDO));
  }

}
