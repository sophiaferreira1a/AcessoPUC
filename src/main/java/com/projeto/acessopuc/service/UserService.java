package com.projeto.acessopuc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.projeto.acessopuc.model.Usuario;

@Service
public class UserService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private long proximoId = 1;

    public List<Usuario> listar() {
        return new ArrayList<>(usuarios);
    }

    public Usuario adicionar(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().isBlank()
                || usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException("Nome e e-mail são obrigatórios");
        }

        usuario.setId(proximoId++);
        usuarios.add(usuario);
        return usuario;
    }
}