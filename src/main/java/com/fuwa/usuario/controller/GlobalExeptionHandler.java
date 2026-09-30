package com.fuwa.usuario.controller;


import com.fuwa.usuario.infrastructure.exeptions.ConflictException;
import com.fuwa.usuario.infrastructure.exeptions.IllegalArgumentsException;
import com.fuwa.usuario.infrastructure.exeptions.ResorceNotFoundException;
import com.fuwa.usuario.infrastructure.exeptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExeptionHandler {

    @ExceptionHandler(ResorceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundExeption(ResorceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> haendleConmflictExeption(ConflictException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handlerUnauthorizedException(UnauthorizedException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(IllegalArgumentsException.class)
    public ResponseEntity<String> handlerIllegalArgumentsException(IllegalArgumentsException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
