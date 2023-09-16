package edu.vsu.putinpa.application.exception;

public class SingletonInstanceNotInitializedException extends IllegalStateException {
    public SingletonInstanceNotInitializedException() {
    }

    public SingletonInstanceNotInitializedException(String s) {
        super(s);
    }
}
