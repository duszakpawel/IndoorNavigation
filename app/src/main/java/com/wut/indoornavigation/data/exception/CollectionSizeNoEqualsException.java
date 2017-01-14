package com.wut.indoornavigation.data.exception;

/**
 * Custom exception thrown when collections don't have equal size
 */
public class CollectionSizeNoEqualsException extends RuntimeException {

    public CollectionSizeNoEqualsException(String message) {
        super(message);
    }
}
