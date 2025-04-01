package com.freibu.compressor.exception;

public class CompressionException extends Exception {

    public CompressionException(String message) {
        super(message);
    }

    public CompressionException(String message, Throwable cause) {
        super(message, cause);
    }
}
