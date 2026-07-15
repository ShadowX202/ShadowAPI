package de.ShadowX202.shadowAPI.command.exception;

import de.ShadowX202.shadowAPI.command.interfaces.argument.Argument;

import java.lang.reflect.Method;
import java.util.List;

public class MissingArgumentException extends RuntimeException {
    public MissingArgumentException(Argument argument) {
        super("Missing argument: " + argument.getName() + " of type " +  returnType(argument));
    }

    public MissingArgumentException() {
    }

    private static String returnType(Argument argument) {
        String type = "Unknown";
        try {
            Method m = argument.getClass().getMethod("parse", List.class);
            type = m.getReturnType().getName();
        } catch (NoSuchMethodException e) {}
        return type;
    }
}
