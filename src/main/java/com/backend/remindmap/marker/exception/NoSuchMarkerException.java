package com.backend.remindmap.marker.exception;

public class NoSuchMarkerException extends RuntimeException {

    public static final String ERROR_MESSAGE = "존재하지 않는 마커 입니다.";

    public NoSuchMarkerException(String message) {
        super(message);
    }

    public NoSuchMarkerException() {
        this(ERROR_MESSAGE);
    }
}
