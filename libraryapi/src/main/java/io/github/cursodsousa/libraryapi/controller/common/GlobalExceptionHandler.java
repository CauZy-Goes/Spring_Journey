package io.github.cursodsousa.libraryapi.controller.common;

import io.github.cursodsousa.libraryapi.controller.dto.ErroCampo;
import io.github.cursodsousa.libraryapi.controller.dto.ErroResposta;
import io.github.cursodsousa.libraryapi.exceptions.CampoInvalidoException;
import io.github.cursodsousa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.cursodsousa.libraryapi.exceptions.RegistroDuplicadoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice // Captura Exceptions
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) //captura esse erro
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) //Tipo de metodo http
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("Erro de validação: {} ", e.getMessage());
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação.",
                listaErros);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)//captura esse erro
    @ResponseStatus(HttpStatus.CONFLICT) //Tipo de metodo http
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e){
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)//captura esse erro
    @ResponseStatus(HttpStatus.BAD_REQUEST) //Tipo de metodo http
    public ErroResposta handleOperacaoNaoPermitidaException(
            OperacaoNaoPermitidaException e){
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)//captura esse erro
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) //Tipo de metodo http
    public ErroResposta handleCampoInvalidoException(CampoInvalidoException e){
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação.",
                List.of(new ErroCampo(e.getCampo(), e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)//captura esse erro
    @ResponseStatus(HttpStatus.FORBIDDEN) //Tipo de metodo http
    public ErroResposta handleAccesDeniedException(AccessDeniedException e){
        return new ErroResposta(HttpStatus.FORBIDDEN.value(), "Acesso Negado.", List.of());
    }

    @ExceptionHandler(RuntimeException.class)//captura esse erro
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //Tipo de metodo http
    public ErroResposta handleErrosNaoTratados(RuntimeException e){
        log.error("Erro inesperado", e);
        return new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado. Entre em contato com a administração."
                , List.of());
    }
}
