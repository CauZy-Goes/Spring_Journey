package io.github.cursodsousa.libraryapi.service;

import io.github.cursodsousa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.Usuario;
import io.github.cursodsousa.libraryapi.repository.AutorRepository;
import io.github.cursodsousa.libraryapi.repository.LivroRepository;
import io.github.cursodsousa.libraryapi.security.SecurityService;
import io.github.cursodsousa.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor //cria um contrutor para os campos final
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;

    public Autor salvar(Autor autor){
        validator.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return repository.save(autor);
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja salvo na base.");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Autor autor){
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException(
                    "Não é permitido excluir um Autor que possui livros cadastrados!");
        }
        repository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if(nome != null){
            return repository.findByNome(nome);
        }

        if(nacionalidade != null){
            return repository.findByNacionalidade(nacionalidade);
        }

        return repository.findAll();
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade){
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        // Configura um ExampleMatcher para definir como os campos devem ser comparados
        ExampleMatcher matcher = ExampleMatcher
                .matching() //define que a correspondência será baseada nos valores do objeto exemplo
                .withIgnorePaths("id", "dataNascimento", "dataCadastro") //igora esses campos
                .withIgnoreNullValues()// Ignora campos nulos no objeto exemplo
                .withIgnoreCase()// Torna a busca case-insensitive (ignora maiúsculas e minúsculas)
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
                // Permite que o nome/nacionalidade contenham o valor buscado
                //(equivalente a LIKE %valor%)
        // Cria um objeto Example com base no autor e nas regras do matcher
        Example<Autor> autorExample = Example.of(autor, matcher);

        // Realiza a busca no repositório com base no Example, retornando todos os registros que correspondem aos critérios
        return repository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
