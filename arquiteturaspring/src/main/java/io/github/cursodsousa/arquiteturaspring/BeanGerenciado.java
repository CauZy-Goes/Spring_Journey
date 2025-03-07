package io.github.cursodsousa.arquiteturaspring;

import io.github.cursodsousa.arquiteturaspring.todos.TodoEntity;
import io.github.cursodsousa.arquiteturaspring.todos.TodoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

//@Scope("singleton") padrao uma unica aplicação, nao preciso criar varias instancias
//@Lazy(false) so é instanciado quando é utilizado
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON) // uso a singleton, enum.
// Scope Prototipe contratio do singleton, cria instacias
//@Scope(WebApplicationContext.SCOPE_APPLICATION)
//@Scope("request") app web = so sobrevive na requisição, exclui no fim da requisição
//@Scope("session") app web = existe enquanto dura a aplicação/sessao
//@Scope("application") so serve em web = fica na sessao de todos os usuarios
public class BeanGerenciado {

    private String idUsuarioLogado;

    @Autowired
    private TodoValidator validator;

    @Autowired
    public BeanGerenciado(TodoValidator validator) {
        this.validator = validator;
    }

    public void utilizar(){
        var todo = new TodoEntity();
        validator.validar(todo);
    }

    @Autowired
    public void setValidator(TodoValidator validator){
        this.validator = validator;
    }
}
