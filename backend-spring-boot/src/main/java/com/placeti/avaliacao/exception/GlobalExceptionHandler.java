package com.placeti.avaliacao.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

//--------------------------------------------------------------------
/** Centraliza o tratamento de erro da API.
 *
 *  Sem isso, buscar um id que não existe devolveria um stack trace e
 *  um 500 genérico, o que não ajuda nem o front nem quem for debugar. */
//--------------------------------------------------------------------
@RestControllerAdvice
public class GlobalExceptionHandler {

    //----------------------------------------------------------
    /** Registro não encontrado */
    //----------------------------------------------------------
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(EntityNotFoundException ex) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("mensagem", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    //----------------------------------------------------------
    /** Erro de validação do corpo da requisição.
     *  Devolve um mapa campo -> mensagem, para o front conseguir
     *  destacar exatamente o campo que veio errado */
    //----------------------------------------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new LinkedHashMap<>();

        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            erros.put(erro.getField(), erro.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(erros);
    }
}
