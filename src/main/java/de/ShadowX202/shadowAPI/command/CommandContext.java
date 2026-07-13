package de.ShadowX202.shadowAPI.command;

import de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class CommandContext implements de.ShadowX202.shadowAPI.command.interfaces.CommandContext {

    private CommandSender sender;
    private ShadowAPICommand command;
    private Map<String, Object> arguments;
    private Map<String, Object> flags;

    public CommandContext(ShadowAPICommand command, CommandSender sender, Map<String, Object> arguments, Map<String, Object> flags) {
        this.sender = sender;
        this.command = command;
        this.arguments = arguments;
        this.flags = flags;
    }

    @Override
    public ShadowAPICommand getCommand() {
        return command;
    }

    @Override
    public CommandSender getSender() {
        return sender;
    }

    @Override
    public boolean isPlayer() {
        return (sender instanceof Player);
    }

    @Override
    public @Nullable Player getPlayer() {
        if (isPlayer()) {
            return (Player) sender;
        }
        return null;
    }

    @Override
    public @Nullable Object getArgument(String name) {
        return arguments.get(name);
    }

    @Override
    public @Nullable Object getFlag(String name) {
        return flags.get(name);
    }
}
