package com.backend.remindmap.route.exception;

public class RouteMemoNullOrEmptyException extends IllegalArgumentException {
    public static final String ERROR_MESSAGE = "비어 있는 루트 입니다.";

    public RouteMemoNullOrEmptyException(String message) {
        super(message);
    }

    public RouteMemoNullOrEmptyException() {
        this(ERROR_MESSAGE);
    }
}
