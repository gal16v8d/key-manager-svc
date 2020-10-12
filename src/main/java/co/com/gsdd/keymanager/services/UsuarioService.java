package co.com.gsdd.keymanager.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.gsdd.keymanager.entities.Usuario;
import co.com.gsdd.keymanager.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

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

}
