package com.merapar.graphql;

public class GraphQlException extends RuntimeException {

    public GraphQlException(String message) {
        super(message);
    }

    public GraphQlException(String message, Throwable cause) {
        super(message, cause);
    }
}
