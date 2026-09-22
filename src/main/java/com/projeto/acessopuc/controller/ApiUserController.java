package com.projeto.acessopuc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.acessopuc.model.Usuario;
import com.projeto.acessopuc.service.ApiUserService;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    private final ApiUserService apiUserService;

    public ApiUserController(ApiUserService apiUserService) {
        this.apiUserService = apiUserService;
    }

    @GetMapping
    public List<Usuario> listar() {
        return apiUserService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario criar(@RequestBody Usuario usuario) {
        return apiUserService.adicionar(usuario);
    }
}