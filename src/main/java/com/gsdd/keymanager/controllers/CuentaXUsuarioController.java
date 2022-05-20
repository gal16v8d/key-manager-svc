package com.gsdd.keymanager.controllers;

import com.gsdd.keymanager.components.CuentaXUsuarioConverter;
import com.gsdd.keymanager.components.CuentaXUsuarioRequestConverter;
import com.gsdd.keymanager.entities.CuentaXUsuario;
import com.gsdd.keymanager.requests.CuentaXUsuarioRequest;
import com.gsdd.keymanager.services.CuentaXUsuarioService;
import com.gsdd.keymanager.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cuenta CRUD")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cuentas")
public class CuentaXUsuarioController {

  private static final String NO_EXISTE_UNA_CUENTA_QUE_COINCIDA_CON_LOS_VALORES_INGRESADOS =
      "No existe una cuenta que coincida con los valores ingresados.";
  private final CuentaXUsuarioService cuentaxusuarioService;
  private final CuentaXUsuarioConverter cuentaXUsuarioConverter;
  private final CuentaXUsuarioRequestConverter cuentaXUsuarioRequestConverter;
  private final UsuarioService usuarioService;

  private Optional<CuentaXUsuario> findByUsernameAndCodigoCuenta(
      String loginUsuario, Long codigoCuenta) {
    return usuarioService
        .findByUsername(loginUsuario)
        .map(cuentaxusuarioService::findByUsuario)
        .orElseGet(Collections::emptyList)
        .stream()
        .filter(cxu -> Objects.equals(codigoCuenta, cxu.getCodigoCuenta()))
        .findAny();
  }

  @Operation(summary = "Permite obtener una cuenta mediante su codigo.")
  @GetMapping("/{loginUsuario}/{codigoCuenta}")
  public ResponseEntity<?> get(
      @PathVariable("loginUsuario") String loginUsuario,
      @PathVariable("codigoCuenta") Long codigoCuenta) {
    return findByUsernameAndCodigoCuenta(loginUsuario, codigoCuenta)
        .map(cuentaXUsuarioConverter::convert)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(
      summary = "Permite obtener una cuenta mediante el usuario (requiere el login del usuario).")
  @GetMapping("/{loginUsuario}")
  public ResponseEntity<?> getAllByUser(@PathVariable("loginUsuario") String loginUsuario) {
    return usuarioService
        .findByUsername(loginUsuario)
        .map(cuentaxusuarioService::findByUsuario)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Permite crear una cuenta.")
  @PostMapping("/{loginUsuario}")
  public ResponseEntity<?> save(
      @PathVariable("loginUsuario") String loginUsuario,
      @Valid @RequestBody CuentaXUsuarioRequest request,
      BindingResult bResultado) {
    if (bResultado.hasErrors()) {
      return new ResponseEntity<>(bResultado.getAllErrors(), HttpStatus.BAD_REQUEST);
    }
    if (!usuarioService.findByUsername(loginUsuario).isPresent()) {
      return new ResponseEntity<>("Usuario no válido", HttpStatus.BAD_REQUEST);
    }
    CuentaXUsuario cuentaXUsuario =
        cuentaxusuarioService.save(cuentaXUsuarioRequestConverter.convert(request));
    return Optional.ofNullable(cuentaXUsuario)
        .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
  }

  @Operation(summary = "Permite actualizar una cuenta.")
  @PutMapping("/{loginUsuario}/{codigoCuenta}")
  public ResponseEntity<?> update(
      @PathVariable("loginUsuario") String loginUsuario,
      @PathVariable("codigoCuenta") Long codigoCuenta,
      @Valid @RequestBody CuentaXUsuarioRequest request,
      BindingResult bResultado) {
    if (bResultado.hasErrors()) {
      return new ResponseEntity<>(bResultado.getAllErrors(), HttpStatus.BAD_REQUEST);
    }
    Optional<CuentaXUsuario> cuentaXUsuarioOp =
        findByUsernameAndCodigoCuenta(loginUsuario, codigoCuenta);
    if (cuentaXUsuarioOp.isPresent()) {
      CuentaXUsuario cuentaXUsuario = cuentaXUsuarioRequestConverter.convert(request);
      cuentaXUsuario.setCodigoCuenta(codigoCuenta);
      cuentaXUsuario.setFecha(Date.from(Instant.now()));
      CuentaXUsuario cuentaActualizada = cuentaxusuarioService.update(cuentaXUsuario);
      return Optional.ofNullable(cuentaActualizada)
          .map(ResponseEntity::ok)
          .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_MODIFIED).build());
    } else {
      return getNotFoundResponse();
    }
  }

  @Operation(summary = "Permite eliminar una cuenta.")
  @DeleteMapping("/{loginUsuario}/{codigoCuenta}")
  public ResponseEntity<?> delete(
      @PathVariable("loginUsuario") String loginUsuario,
      @PathVariable("codigoCuenta") Long codigoCuenta) {
    return findByUsernameAndCodigoCuenta(loginUsuario, codigoCuenta)
        .map(
            (CuentaXUsuario cxu) -> {
              cuentaxusuarioService.delete(codigoCuenta);
              return ResponseEntity.noContent().build();
            })
        .orElseGet(this::getNotFoundResponse);
  }

  private ResponseEntity<Object> getNotFoundResponse() {
    return new ResponseEntity<>(
        NO_EXISTE_UNA_CUENTA_QUE_COINCIDA_CON_LOS_VALORES_INGRESADOS, HttpStatus.NOT_FOUND);
  }
}
