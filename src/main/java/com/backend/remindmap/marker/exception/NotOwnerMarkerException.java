package com.backend.remindmap.marker.exception;

public class NotOwnerMarkerException extends RuntimeException {

    public static final String ERROR_MESSAGE = "마커의 생성자가 아닙니다.";

    public NotOwnerMarkerException() {
        this(ERROR_MESSAGE);
    }

    public NotOwnerMarkerException(String message) {
        super(message);
    }
}
