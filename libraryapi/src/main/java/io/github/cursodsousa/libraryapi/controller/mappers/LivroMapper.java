package io.github.cursodsousa.libraryapi.controller.mappers;

import io.github.cursodsousa.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.cursodsousa.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.cursodsousa.libraryapi.model.Livro;
import io.github.cursodsousa.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

// Definição do Mapper, indicando que ele será gerenciado pelo Spring e usa
//AutorMapper para conversões relacionadas a 'Autor'
@Mapper(componentModel = "spring", uses = AutorMapper.class )
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    /**
     * Método que converte um CadastroLivroDTO (DTO de entrada) para a entidade Livro.
     *
     * - O campo 'autor' no Livro precisa de um objeto Autor, mas no DTO temos apenas
     o UUID do autor.
     * - Para resolver isso, usamos uma **expressão personalizada** do MapStruct
     (`expression`).
     * - Essa expressão busca o Autor no banco de dados pelo `idAutor()` do DTO.
     * - Caso o Autor não seja encontrado, será atribuído `null` ao campo 'autor'.
     */
    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    /**
     * Método que converte um Livro (entidade do banco) para um
     ResultadoPesquisaLivroDTO.
     *
     * - No DTO de resultado, ao invés de retornar apenas o UUID do autor,
     ele retorna um AutorDTO completo.
     * - Como a classe já usa `AutorMapper`, o MapStruct se encarrega de converter o
     `Autor` para `AutorDTO`.
     */
    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
