package com.freibu.compressor.exception;

public class FileCompressionException extends Exception {

    public FileCompressionException(String message) {
        super(message);
    }

    public FileCompressionException(String message, Throwable cause) {
        super(message, cause);
    }
}
