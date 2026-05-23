package com.mercadolivre.cliente_service.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorApiResponse> handleApiException(ApiException ex) {
        return ex.buildErrorResponseEntity();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorApiResponse> handleValidation(MethodArgumentNotValidException ex,
                                                             HttpServletRequest request) {

        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Erro de validação");

        log.warn("Erro de validação: {}", msg);

        ErrorApiResponse response = ErrorApiResponse.builder()
                .message("Erro de validação")
                .description(msg)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorApiResponse> handleMalformedJson(HttpMessageNotReadableException ex) {
        log.warn("JSON mal formatado: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(
                ErrorApiResponse.builder()
                        .message("Corpo da requisição mal formatado")
                        .description("Verifique se o JSON enviado é válido.")
                        .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorApiResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.error("Violação de integridade de dados: ", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ErrorApiResponse.builder()
                        .message("Violação de integridade de dados")
                        .description("O recurso já existe ou há conflito com dados existentes.")
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApiResponse> handleGeneric(Exception ex,
                                                           HttpServletRequest request) {

        log.error("Erro inesperado: ", ex);

        ErrorApiResponse response = ErrorApiResponse.builder()
                .message("Erro interno no servidor")
                .description(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
