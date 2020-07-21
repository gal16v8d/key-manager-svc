package co.com.gsdd.keymanager.components;

import java.util.Optional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import co.com.gsdd.keymanager.entities.CuentaXUsuario;
import co.com.gsdd.keymanager.entities.Usuario;
import co.com.gsdd.keymanager.requests.CuentaXUsuarioRequest;
import co.com.gsdd.keymanager.utils.CifradoKeyManager;

@Component
public class CuentaXUsuarioConverter implements Converter<CuentaXUsuario, CuentaXUsuarioRequest> {

    @Override
    public CuentaXUsuarioRequest convert(CuentaXUsuario source) {
        Optional<CuentaXUsuario> requestOp = Optional.ofNullable(source);
        if (requestOp.isPresent()) {
            Optional<Usuario> usuarioOp = Optional.ofNullable(source.getUsuario());
            if (usuarioOp.isPresent()) {
                CuentaXUsuarioRequest request = new CuentaXUsuarioRequest();
                request.setNombreCuenta(source.getNombreCuenta());
                request.setUrl(source.getUrl());
                request.setUsername(source.getUsername());
                request.setPassword(CifradoKeyManager.descifrarKM(source.getPassword()));
                request.setLoginUsuario(usuarioOp.get().getUsername());
                return request;
            }
        }
        return null;
    }

}
