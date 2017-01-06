package com.wut.indoornavigation.data.exception;

/**
 * Custom exception thrown when map is not found
 */
public class MapNotFoundException extends RuntimeException {

    public MapNotFoundException(String message) {
        super(message);
    }
}
