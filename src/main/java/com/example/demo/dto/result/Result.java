package com.example.demo.dto.result;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author vghosthunter
 */
@Data
public class Result<T> {

    private String msg;

    private int code;

    private T data;

    public Result(HttpStatus status) {
        this.code = status.value();
        this.msg = status.getReasonPhrase();
    }

    public Result(HttpStatus status, T data) {
        this(status);
        this.data = data;
    }

    public Result(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static Result succeeded() {
        return new Result(HttpStatus.OK);
    }

    public static <T> Result succeeded(T data) {
        Result result = new Result(HttpStatus.OK);
        result.setData(data);
        return result;
    }
}
