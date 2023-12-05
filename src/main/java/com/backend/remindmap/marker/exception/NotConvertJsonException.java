package com.backend.remindmap.marker.exception;

public class NotConvertJsonException extends RuntimeException {

    public static final String ERROR_MESSAGE = "마커의 JSON 변환에 실패하였습니다.";

    public NotConvertJsonException(String message) {
        super(message);
    }

    public NotConvertJsonException() {
        this(ERROR_MESSAGE);
    }
}
