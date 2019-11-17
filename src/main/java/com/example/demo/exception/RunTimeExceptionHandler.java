package com.example.demo.exception;

import com.example.demo.dto.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

/**
 * @author vghosthunter
 */
@RestControllerAdvice
public class RunTimeExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result exceptionHandler(EntityNotFoundException e) {
        e.printStackTrace();
        return new Result<>(HttpStatus.NOT_FOUND, e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result exceptionHandler(Exception e){
        e.printStackTrace();
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
