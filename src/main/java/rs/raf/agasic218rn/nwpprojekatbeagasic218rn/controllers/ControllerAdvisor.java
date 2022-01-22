package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.controllers;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.exceptions.ConcurrentOperationException;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.exceptions.InvalidMachineStateException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConcurrentOperationException.class)
    public ResponseEntity<?> handleConcurrentOperationException(ConcurrentOperationException exception) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidMachineStateException.class)
    public ResponseEntity<?> handleMachineStateException(InvalidMachineStateException exception) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        errorResponse.put("machine_status", exception.getStatus().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintException() {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Entered email is already registered on the service.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> handleSQLException() {
        return handleConstraintException();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleIntegrityException() {
        return handleConstraintException();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleCredentialsException() {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Entered email or password were incorrect, please try again.");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UsernameNotFoundException exception) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
