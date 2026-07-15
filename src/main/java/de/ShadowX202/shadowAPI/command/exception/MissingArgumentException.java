package de.ShadowX202.shadowAPI.command.exception;

import de.ShadowX202.shadowAPI.command.interfaces.argument.Argument;

import java.lang.reflect.Method;

public class MissingArgumentException extends RuntimeException {
    public MissingArgumentException(Argument argument) {
        super("Missing argument: " + argument.getName() + " of type " +  returnType(argument));
    }

    public MissingArgumentException() {
    }

    private static String returnType(Argument argument) {
        String type = "Unknown";
        try {
            Method m = argument.getClass().getMethod("parse");
            type = m.getReturnType().getName();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return type;
    }
}
