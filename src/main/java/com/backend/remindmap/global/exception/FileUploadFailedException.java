package com.backend.remindmap.global.exception;

public class FileUploadFailedException extends RuntimeException {

    public static final String ERROR_MESSAGE = "파일 업로드를 실패하였습니다";

    public FileUploadFailedException() {
        this(ERROR_MESSAGE);
    }

    public FileUploadFailedException(String message) {
        super(message);
    }
}
