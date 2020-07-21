package co.com.gsdd.keymanager.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.com.gsdd.keymanager.entities.Usuario;
import co.com.gsdd.keymanager.exceptions.ContrasenaException;
import co.com.gsdd.keymanager.repositories.UsuarioRepository;
import co.com.gsdd.keymanager.utils.CifradoKeyManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService implements UserDetailsService {

    private static final String USUARIO_NO_VALIDO = "Usuario no válido";

    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOp = findByUsername(username);
        log.info("Encontrado {}", usuarioOp.isPresent());
        return usuarioOp.map(usuario -> new User(usuario.getUsername(),
                passwordEncoder().encode(CifradoKeyManager.descifrarKM(usuario.getPassword())), new ArrayList<>()))
                .orElseThrow(() -> new ContrasenaException(USUARIO_NO_VALIDO));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
