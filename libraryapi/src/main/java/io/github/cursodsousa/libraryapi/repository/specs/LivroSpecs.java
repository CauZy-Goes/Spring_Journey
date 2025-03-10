package io.github.cursodsousa.libraryapi.repository.specs;

import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {  // Classe responsável por definir Specifications para
// a entidade Livro

    /**
     * Cria uma especificação para buscar livros com um ISBN específico.
     *
     * - Utiliza `cb.equal()` para comparar o campo "isbn" da entidade Livro
     com o valor informado.
     */
    public static Specification<Livro> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    /**
     * Cria uma especificação para buscar livros cujo título contenha uma
     determinada palavra/frase.
     *
     * - Usa `cb.like()` para buscar títulos parcialmente correspondentes
     (LIKE no SQL).
     * - Converte o título para maiúsculas (`cb.upper()`) para tornar a busca
     **case insensitive**.
     * - O valor pesquisado também é convertido para maiúsculas e envolvido por
     `%`, permitindo busca parcial.
     *
     * Exemplo de SQL gerado:
     * `UPPER(livro.titulo) LIKE '%titulo%'`
     */
    public static Specification<Livro> tituloLike(String titulo) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    /**
     * Cria uma especificação para buscar livros de um determinado gênero.
     *
     * - Utiliza `cb.equal()` para comparar o campo "genero" da entidade Livro
     com o valor passado.
     */
    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }

    /**
     * Cria uma especificação para buscar livros publicados em um determinado ano.
     *
     * - O campo "dataPublicacao" geralmente é do tipo `LocalDate` ou `Date`,
     mas queremos filtrar pelo **ano**.
     * - `cb.function("to_char", String.class, root.get("dataPublicacao"), cb.
     literal("YYYY"))`
     *   → Converte a data para o formato **YYYY** (extraindo o ano da data de
     publicação).
     * - Compara esse ano convertido com o `anoPublicacao` passado.
     *
     * Exemplo de SQL gerado:
     * `TO_CHAR(data_publicacao, 'YYYY') = '2024'`
     */
    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao) {
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class,
                        root.get("dataPublicacao"), cb.literal("YYYY")), anoPublicacao.toString());
    }

    /**
     * Cria uma especificação para buscar livros pelo nome do autor.
     *
     * - Como "autor" é um relacionamento, é necessário fazer um JOIN com a
     entidade `Autor`.
     * - `root.join("autor", JoinType.INNER)` → Realiza um **INNER JOIN** entre
     Livro e Autor.
     * - `cb.like(cb.upper(joinAutor.get("nome")), "%" + nome.toUpperCase() + "%")`
     *   → Busca autores cujo nome contenha a palavra informada, ignorando
     maiúsculas/minúsculas.
     *
     * Exemplo de SQL gerado:
     * `INNER JOIN autor ON livro.autor_id = autor.id
     * WHERE UPPER(autor.nome) LIKE '%NOME%'`
     */
    public static Specification<Livro> nomeAutorLike(String nome) {
        return (root, query, cb) -> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.INNER);
            return cb.like(cb.upper(joinAutor.get("nome")), "%" + nome.toUpperCase() + "%");
        };
    }
}

