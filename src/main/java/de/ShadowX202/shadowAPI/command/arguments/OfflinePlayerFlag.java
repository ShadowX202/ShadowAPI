package de.ShadowX202.shadowAPI.command.arguments;

import de.ShadowX202.shadowAPI.command.exception.ParseArgumentException;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Flag;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class OfflinePlayerFlag implements Flag<OfflinePlayer> {

    private String name;
    private List<String> aliases;
    private boolean optional;

    public OfflinePlayerFlag(String name, String ...aliases) {
        this.name = name;
        this.aliases = Arrays.asList(aliases);
        this.optional = false;
    }

    public OfflinePlayerFlag setOptional(boolean optional) {
        this.optional = optional;
        return this;
    }

    @Override
    public @NotNull List<String> getAliases() {
        return List.of();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isOptional() {
        return this.optional;
    }

    @Override
    public OfflinePlayer parse(@Nullable List<String> args) throws ParseArgumentException {
        if(args == null){
            throw new ParseArgumentException("No name provided");
        }
        return Bukkit.getOfflinePlayer(name);
    }

    @Override
    public List<String> tab(@Nullable List<String> args) {
        if(args.size() != 1) return List.of();
        return Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).toList();
    }
}
