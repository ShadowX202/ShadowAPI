package de.ShadowX202.shadowAPI.command.interfaces.argument;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Tabable {
    List<String> tab(@Nullable List<String> args);
}
