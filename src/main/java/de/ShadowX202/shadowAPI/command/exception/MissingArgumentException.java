package de.ShadowX202.shadowAPI.command.exception;

public class MissingArgumentException extends RuntimeException {
    public MissingArgumentException(String message) {
        super(message);
    }

    public MissingArgumentException() {
    }
}
