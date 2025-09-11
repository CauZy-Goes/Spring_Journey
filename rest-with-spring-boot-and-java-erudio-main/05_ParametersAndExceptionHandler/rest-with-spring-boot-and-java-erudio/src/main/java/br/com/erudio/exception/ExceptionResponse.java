package br.com.erudio.exception;

import java.util.Date;

public record ExceptionResponse(
        Date timestamp,
        String message,
        String details) {}
// formta a exceção em json
// classes que apenas armazenam valores, nao modifica os valores, so armazena, imutabilidade, explicita