package co.com.gsdd.keymanager.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Auth")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @ApiOperation(value = "Permite autenticar a un usuario.")
    @GetMapping("/login")
    public ResponseEntity<?> basicAuth() {
        return ResponseEntity.status(HttpStatus.OK).body("Autenticado");
    }
}
