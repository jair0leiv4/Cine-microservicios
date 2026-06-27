package cl.duoc.producto.exception;


import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> manejarValidaciones(
            MethodArgumentNotValidException ex){

        Map<String,String> errores = new LinkedHashMap<>();

        ex.getBindingResult()

                .getFieldErrors()

                .forEach(error ->

                        errores.put(

                                error.getField(),

                                error.getDefaultMessage()

                        )

                );

        return ResponseEntity

                .badRequest()

                .body(errores);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,String>> manejarRuntime(

            RuntimeException ex){

        Map<String,String> error = new LinkedHashMap<>();

        error.put("error", ex.getMessage());

        return ResponseEntity

                .status(HttpStatus.BAD_REQUEST)

                .body(error);

    }

}