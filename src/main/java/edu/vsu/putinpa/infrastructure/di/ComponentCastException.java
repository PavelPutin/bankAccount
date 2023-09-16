package edu.vsu.putinpa.infrastructure.di;

public class ComponentCastException extends RuntimeException {
    public ComponentCastException(String message, Throwable cause) {
        super(message, cause);
    }
}
