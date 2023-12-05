package com.backend.remindmap.global.exception;

public class FileEmptyException extends RuntimeException {

    public static final String ERROR_MESSAGE = "업로드 파일은 비어있을 수 없습니다.";

    public FileEmptyException() {
        this(ERROR_MESSAGE);
    }

    public FileEmptyException(String message) {
        super(message);
    }
}
