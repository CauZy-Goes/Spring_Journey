package io.github.cursodsousa.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Autor") //nome swagger
public record AutorDTO( //Classe, Imutavel(so tem get), Simples; Ja vem com um contructor
        UUID id,
        @NotBlank(message = "campo obrigatorio") // Campo não pode ser uma string vazia nem nula
        @Size(min = 2, max = 100, message = "campo fora do tamanho padrao") // define um tamanho padrao
        @Schema(name = "nome") //nome swagger
        String nome,
        @NotNull(message = "campo obrigatorio")// nao pode ser nulo
        @Past(message = "nao pode ser uma data futura") // nao pode ser um data posterior a de hoje
        @Schema(name = "dataNascimento") //nome swagger
        LocalDate dataNascimento,
        @NotBlank(message = "campo obrigatorio") // nao pode ser branco
        @Size(max = 50, min = 2, message = "campo fora do tamanho padrao")
        @Schema(name = "nacionalidade") //nome swagger
        String nacionalidade
) {
}
