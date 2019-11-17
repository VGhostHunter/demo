package com.example.demo.exception;

import com.example.demo.dto.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author vghosthunter
 */
@Slf4j
@RestControllerAdvice
public class BindingExceptionProcess {

    /**
     * 用来处理没有加@RequestBody的binding
     * @param e e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result validExceptionHandler(BindException e) {
        StringBuilder s = new StringBuilder();
        e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .forEach(msg -> {
                    s.append(msg).append(";");
                });
        log.info(s.toString());
        return new Result<>(HttpStatus.BAD_REQUEST, s.toString());
    }

    /**
     * 用来处理加了@RequestBody的binding
     * @param e e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result validExceptionHandler(MethodArgumentNotValidException e) {
        StringBuilder s = new StringBuilder();
        e.getBindingResult().getAllErrors()
                .forEach(error -> {
                    s.append(error.getDefaultMessage()).append(";");
                });
        log.info(s.toString());
        return new Result<>(HttpStatus.BAD_REQUEST, s.toString());
    }
}
