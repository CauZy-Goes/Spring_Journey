package io.github.cursodsousa.arquiteturaspring.montadora.api;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //PADRAO
@Target({ElementType.FIELD, ElementType.METHOD}) // ALVO TIPOS E METHODS, METHODOS E VARIAVEIS
@Qualifier("motorAspirado") // COLOQUEI O QUALIFIER AQUI, AGREGAÇÃO DE ANNOTATION
public @interface Aspirado { //  TODOS OS ELEMENTOS ANOTADOS COM ESSE BEAN SERAO ANOTADOS PELOS ACIMA
}
