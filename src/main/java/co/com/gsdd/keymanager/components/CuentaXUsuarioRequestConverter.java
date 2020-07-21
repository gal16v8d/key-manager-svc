package co.com.gsdd.keymanager.components;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import co.com.gsdd.keymanager.entities.CuentaXUsuario;
import co.com.gsdd.keymanager.entities.CuentaXUsuario.CuentaXUsuarioBuilder;
import co.com.gsdd.keymanager.entities.Usuario;
import co.com.gsdd.keymanager.requests.CuentaXUsuarioRequest;
import co.com.gsdd.keymanager.services.UsuarioService;
import co.com.gsdd.keymanager.utils.CifradoKeyManager;

@Component
public class CuentaXUsuarioRequestConverter implements Converter<CuentaXUsuarioRequest, CuentaXUsuario> {

    private final UsuarioService usuarioService;

    @Autowired
    public CuentaXUsuarioRequestConverter(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public CuentaXUsuario convert(CuentaXUsuarioRequest source) {
        Optional<CuentaXUsuarioRequest> requestOp = Optional.ofNullable(source);
        if (requestOp.isPresent()) {
            Optional<Usuario> usuarioOp = usuarioService.findByUsername(source.getLoginUsuario());
            if (usuarioOp.isPresent()) {
                CuentaXUsuarioBuilder builder = CuentaXUsuario.builder();
                builder.nombreCuenta(source.getNombreCuenta());
                builder.url(source.getUrl());
                builder.username(source.getUsername());
                builder.password(CifradoKeyManager.cifrarKM(source.getPassword()));
                builder.usuario(usuarioOp.get());
                builder.fecha(Date.from(Instant.now()));
                return builder.build();
            }
        }
        return null;
    }

}
