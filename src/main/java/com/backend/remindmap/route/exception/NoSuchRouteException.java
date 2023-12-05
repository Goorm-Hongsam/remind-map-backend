package com.backend.remindmap.route.exception;

public class NoSuchRouteException extends RuntimeException {
    public static final String ERROR_MESSAGE = "존재하지 않는 루트 입니다.";

    public NoSuchRouteException(String message) {
        super(message);
    }

    public NoSuchRouteException() {
        this(ERROR_MESSAGE);
    }

}
