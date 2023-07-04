package com.master.amsterdam.exception;

public class UnauthorizedException extends RuntimeException  {

    public UnauthorizedException(String message) {
        super(message);
    }
}
