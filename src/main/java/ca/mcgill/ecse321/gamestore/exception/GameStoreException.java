package ca.mcgill.ecse321.gamestore.exception;

import org.springframework.http.HttpStatus;

public class GameStoreException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private HttpStatus status;

    public GameStoreException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}