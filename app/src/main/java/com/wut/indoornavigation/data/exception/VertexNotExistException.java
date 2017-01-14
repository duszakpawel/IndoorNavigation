package com.wut.indoornavigation.data.exception;

/**
 * Custom exception thrown when vertex doesn't belong to the graph
 */
public class VertexNotExistException extends RuntimeException {

    public VertexNotExistException(String message) {
        super(message);
    }
}
