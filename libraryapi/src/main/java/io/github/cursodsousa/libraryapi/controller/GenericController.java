package io.github.cursodsousa.libraryapi.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public interface GenericController {

    default URI gerarHeaderLocation(UUID id){
        return ServletUriComponentsBuilder // cria uma url da response
                .fromCurrentRequest() //uri atual
                .path("/{id}")// caminho dps no caminho base
                .buildAndExpand(id) // variabel inserida dentro no path adicionado/ no caso o id da entidade
                .toUri(); //coverte para o path
    }
}
