package com.gsdd.keymanager.controllers;

import com.gsdd.keymanager.components.UsuarioRequestConverter;
import com.gsdd.keymanager.entities.Usuario;
import com.gsdd.keymanager.requests.UsuarioRequest;
import com.gsdd.keymanager.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Usuarios CRUD")
@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

  private static final String NO_EXISTE_UN_USUARIO_QUE_COINCIDA_CON_LOS_VALORES_INGRESADOS =
      "No existe un usuario que coincida con los valores ingresados.";
  private final UsuarioService usuarioService;
  private final UsuarioRequestConverter usuarioRequestConverter;

  private Optional<Usuario> findByCodigoUsuario(Long codigoUsuario) {
    return usuarioService.findByCodigoUsuario(codigoUsuario);
  }

  @Operation(summary = "Permite obtener un usuario mediante su codigo.")
  @GetMapping
  public ResponseEntity<?> getByLogin(@RequestParam("login") String login) {
    return usuarioService
        .findByUsername(login)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Permite obtener un usuario mediante su codigo.")
  @GetMapping("/{codigoUsuario}")
  public ResponseEntity<?> get(@PathVariable("codigoUsuario") Long codigoUsuario) {
    return findByCodigoUsuario(codigoUsuario)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Permite crear un usuario.")
  @PostMapping
  public ResponseEntity<?> save(
      @Valid @RequestBody UsuarioRequest request, BindingResult bResultado) {
    if (bResultado.hasErrors()) {
      return new ResponseEntity<>(bResultado.getAllErrors(), HttpStatus.BAD_REQUEST);
    }
    Usuario usuario = usuarioService.save(usuarioRequestConverter.convert(request));
    return Optional.ofNullable(usuario)
        .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
  }

  @Operation(summary = "Permite actualizar un usuario.")
  @PutMapping("/{codigoUsuario}")
  public ResponseEntity<?> update(
      @PathVariable("codigoUsuario") Long codigoUsuario,
      @Valid @RequestBody UsuarioRequest request,
      BindingResult bResultado) {
    if (bResultado.hasErrors()) {
      return new ResponseEntity<>(bResultado.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    Optional<Usuario> usuarioOp = findByCodigoUsuario(codigoUsuario);
    if (usuarioOp.isPresent()) {
      Usuario usuario = usuarioRequestConverter.convert(request);
      usuario.setCodigoUsuario(codigoUsuario);
      Usuario usuarioActualizado = usuarioService.update(usuario);
      return Optional.ofNullable(usuarioActualizado)
          .map(ResponseEntity::ok)
          .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_MODIFIED).build());
    } else {
      return getNotFoundResponse();
    }
  }

  @Operation(summary = "Permite eliminar un usuario.")
  @DeleteMapping("/{codigoUsuario}")
  public ResponseEntity<?> delete(@PathVariable("codigoUsuario") Long codigoUsuario) {
    return findByCodigoUsuario(codigoUsuario)
        .map(
            (Usuario u) -> {
              usuarioService.delete(codigoUsuario);
              return ResponseEntity.noContent().build();
            })
        .orElseGet(this::getNotFoundResponse);
  }

  private ResponseEntity<Object> getNotFoundResponse() {
    return new ResponseEntity<>(
        NO_EXISTE_UN_USUARIO_QUE_COINCIDA_CON_LOS_VALORES_INGRESADOS, HttpStatus.NOT_FOUND);
  }
}
