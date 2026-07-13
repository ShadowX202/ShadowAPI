package de.ShadowX202.shadowAPI.command.interfaces.argument;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Tabable {
    List<String> tab(String[] args, @Nullable Integer index);
}
