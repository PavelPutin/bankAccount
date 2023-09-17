package edu.vsu.putinpa.infrastructure.di;

public class ComponentNotFoundException extends RuntimeException {
    public ComponentNotFoundException(String message) {
        super(message);
    }

    public ComponentNotFoundException(Throwable cause) {
        super(cause);
    }
}
