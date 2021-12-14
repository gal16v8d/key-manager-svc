package com.gsdd.keymanager.components;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.gsdd.keymanager.entities.CuentaXUsuario;
import com.gsdd.keymanager.entities.Usuario;
import com.gsdd.keymanager.entities.CuentaXUsuario.CuentaXUsuarioBuilder;
import com.gsdd.keymanager.requests.CuentaXUsuarioRequest;
import com.gsdd.keymanager.services.UsuarioService;
import com.gsdd.keymanager.utils.CifradoKeyManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CuentaXUsuarioRequestConverter
    implements Converter<CuentaXUsuarioRequest, CuentaXUsuario> {

  private final UsuarioService usuarioService;

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
