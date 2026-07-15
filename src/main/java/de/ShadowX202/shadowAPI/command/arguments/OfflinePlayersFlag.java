package de.ShadowX202.shadowAPI.command.arguments;

import de.ShadowX202.shadowAPI.command.exception.ParseArgumentException;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Flag;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OfflinePlayersFlag implements Flag<List<OfflinePlayer>> {

    private String name;
    private List<String> aliases;
    private boolean optional;
    private int max;

    public OfflinePlayersFlag(String name, String ...aliases) {
        this.name = name;
        this.aliases = Arrays.asList(aliases);
        this.optional = false;
    }

    public OfflinePlayersFlag setOptional(boolean optional) {
        this.optional = optional;
        return this;
    }

    public OfflinePlayersFlag setMax(int max) {
        this.max = max;
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
    public List<OfflinePlayer> parse(@Nullable List<String> args) throws ParseArgumentException {
        List<OfflinePlayer> offlinePlayers = new ArrayList<>();
        for(String arg: args) {
            offlinePlayers.add(Bukkit.getOfflinePlayer(arg));
        }
        if(max > 0 && max < offlinePlayers.size()) {
            throw new ParseArgumentException("The maximum number of offline players is " + max);
        }
        return offlinePlayers;
    }

    @Override
    public List<String> tab(@Nullable List<String> args) {
        return Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).toList();
    }
}
