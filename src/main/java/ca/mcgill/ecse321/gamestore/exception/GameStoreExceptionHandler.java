package ca.mcgill.ecse321.gamestore.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GameStoreExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(GameStoreException.class)
    public ResponseEntity<String> handleGameStoreException(GameStoreException e) {
        return new ResponseEntity<String>(e.getMessage(), e.getStatus());
    }
}