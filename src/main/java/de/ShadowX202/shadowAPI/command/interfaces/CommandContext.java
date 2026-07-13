package de.ShadowX202.shadowAPI.command.interfaces;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public interface CommandContext {
    ShadowAPICommand getCommand();
    CommandSender getSender();

    boolean isPlayer();
    @Nullable Player getPlayer();

    Object getArgument(String name);
    Object getFlag(String name);
}
