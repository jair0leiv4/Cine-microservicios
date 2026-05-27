package cl.duoc.venta.exception;

import java.util.LinkedHashMap; // Importa la clase LinkedHashMap para crear un mapa que mantenga el orden de los errores
import java.util.Map; // Importa la clase Map para crear un mapa que almacene los errores de validación y los mensajes de error

import org.springframework.http.HttpStatus; // Importa la clase HttpStatus para establecer el código de estado HTTP en las respuestas
import org.springframework.http.ResponseEntity; // Importa la clase ResponseEntity para crear respuestas HTTP con un cuerpo y un código de estado específico
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>>
    manejarValidaciones(
            MethodArgumentNotValidException ex) { // Maneja las validaciones de los DTOs

        Map<String, String> errores = // Crea un mapa para almacenar los errores de validación
                new LinkedHashMap<>(); // LinkedHashMap para mantener el orden de los errores

        ex.getBindingResult() //        Obtiene los errores de validación del objeto que se está validando
                .getFieldErrors() //        Obtiene los errores de validación de los campos del objeto
                .forEach(error -> //        Itera sobre cada error de validación y los agrega al mapa de errores
                        errores.put( //        Agrega el nombre del campo y el mensaje de error al mapa de errores
                                error.getField(), //        Obtiene el nombre del campo que tiene el error de validación
                                error.getDefaultMessage() //        Obtiene el mensaje de error de validación para el campo que tiene el error
                        )
                );

        return ResponseEntity //Devuelve una respuesta HTTP con el código de estado 400 (Bad Request) y el mapa de errores en el cuerpo de la respuesta
                .badRequest()//        Establece el código de estado HTTP a 400 (Bad Request)
                .body(errores);//        Devuelve el mapa de errores en el cuerpo de la respuesta
    }

    @ExceptionHandler(RuntimeException.class) // Maneja las excepciones de tipo RuntimeException
    public ResponseEntity<Map<String, String>> // Devuelve una respuesta HTTP con el código de estado 400 (Bad Request) y un mensaje de error en el cuerpo de la respuesta
    manejarRuntime( // Maneja las excepciones de tipo RuntimeException
            RuntimeException ex) { // Recibe la excepción de tipo RuntimeException que se ha lanzado

        Map<String, String> error = // Crea un mapa para almacenar el mensaje de error
                new LinkedHashMap<>(); // LinkedHashMap para mantener el orden de los errores

        error.put("error", ex.getMessage()); // Agrega el mensaje de error al mapa de errores con la clave "error"

        return ResponseEntity // Devuelve una respuesta HTTP con el código de estado 400 (Bad Request) y el mapa de errores en el cuerpo de la respuesta
                .status(HttpStatus.BAD_REQUEST) // Establece el código de estado HTTP a 400 (Bad Request)
                .body(error); // Devuelve el mapa de errores en el cuerpo de la respuesta
    }
}