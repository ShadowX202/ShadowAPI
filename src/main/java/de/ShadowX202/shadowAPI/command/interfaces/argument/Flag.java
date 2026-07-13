package de.ShadowX202.shadowAPI.command.interfaces.argument;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Flag<T> extends Argument<T> {
    @NotNull List<String> getAliases();
}
