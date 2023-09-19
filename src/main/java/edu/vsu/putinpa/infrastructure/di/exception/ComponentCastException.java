package edu.vsu.putinpa.infrastructure.di.exception;

public class ComponentCastException extends RuntimeException {
    public ComponentCastException(String message, Throwable cause) {
        super(message, cause);
    }
}
