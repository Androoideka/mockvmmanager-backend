package rs.raf.agasic218rn.domaci3beagasic218rn.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintException() {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Your email is already registered on the service.");
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UsernameNotFoundException exception) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception exception) {

        Map<String, Object> body = new HashMap<>();
        body.put("message", exception.getMessage());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        System.out.println(exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
