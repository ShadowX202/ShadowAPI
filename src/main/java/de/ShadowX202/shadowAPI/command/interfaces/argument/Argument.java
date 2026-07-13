package de.ShadowX202.shadowAPI.command.interfaces.argument;

import de.ShadowX202.shadowAPI.command.exception.ParseArgumentException;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Argument<T> extends Tabable{

    String getName();
    boolean isOptional();

    T parse(String[] args, @Nullable Integer index) throws ParseArgumentException;
}
