package edu.vsu.putinpa.exception;

public class SingletonInstanceNotInitializedException extends IllegalStateException {
    public SingletonInstanceNotInitializedException() {
    }

    public SingletonInstanceNotInitializedException(String s) {
        super(s);
    }
}
