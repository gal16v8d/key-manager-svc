package co.com.gsdd.keymanager.components;

import java.util.Objects;
import java.util.Optional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import co.com.gsdd.keymanager.entities.Usuario;
import co.com.gsdd.keymanager.entities.Usuario.UsuarioBuilder;
import co.com.gsdd.keymanager.exceptions.ContrasenaException;
import co.com.gsdd.keymanager.requests.UsuarioRequest;
import co.com.gsdd.keymanager.utils.CifradoKeyManager;

@Component
public class UsuarioRequestConverter implements Converter<UsuarioRequest, Usuario> {

    @Override
    public Usuario convert(UsuarioRequest source) {
        Optional<UsuarioRequest> requestOp = Optional.ofNullable(source);
        if (requestOp.isPresent()) {
            if (Objects.equals(source.getPassword(), source.getPasswordAgain())) {
                UsuarioBuilder builder = Usuario.builder();
                builder.primerNombre(source.getPrimerNombre()).primerApellido(source.getPrimerApellido());
                builder.username(source.getUsername());
                builder.password(CifradoKeyManager.cifrarKM(source.getPassword()));
                return builder.build();
            }
            throw new ContrasenaException("Las contraseñas no coinciden, favor verificar.");
        }
        return null;
    }

}
