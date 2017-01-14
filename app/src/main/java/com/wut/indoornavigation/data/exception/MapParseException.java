package com.wut.indoornavigation.data.exception;

/**
 * Custom exception thrown when map parse error occurs
 */
public class MapParseException extends RuntimeException {

    public MapParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
