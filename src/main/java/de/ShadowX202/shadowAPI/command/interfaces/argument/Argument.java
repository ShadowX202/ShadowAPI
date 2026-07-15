package de.ShadowX202.shadowAPI.command.interfaces.argument;

import de.ShadowX202.shadowAPI.command.exception.ParseArgumentException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Argument<T> extends Tabable{

    String getName();
    boolean isOptional();

    // args is null if argument/ Flag is not provided, only happens if isOptional() is True
    T parse(@Nullable List<String> args) throws ParseArgumentException;
}
