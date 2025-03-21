package io.github.cursodsousa.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Getter //geras os gettes
@Setter // gerar o stter em compilaçao
@ToString(exclude = {"livros"}) //tirar livros da impressao do toString
@EntityListeners(AuditingEntityListener.class)
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID) //gera automaticamente o UUID
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false) // 100 posoções, nao pode ser null
    private String nome;

    @Column(name = "data_nascimento", nullable = false) // nao pode ser null
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)// 50 posoções, nao pode ser null
    private String nacionalidade;

    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY
//            , cascade = CascadeType.ALL
    )
    private List<Livro> livros;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
