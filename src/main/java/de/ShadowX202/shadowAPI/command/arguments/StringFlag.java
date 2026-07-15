package de.ShadowX202.shadowAPI.command.arguments;

import de.ShadowX202.shadowAPI.command.exception.ParseArgumentException;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Flag;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringFlag implements Flag<String> {

    private String name;
    private List<String> aliases;
    private boolean optional;
    private String defaultValue;

    public StringFlag(String name, String ...aliases) {
        this.name = name;
        this.aliases = Arrays.asList(aliases);
        this.optional = false;
    }

    public StringFlag setOptional(boolean optional) {
        this.optional = optional;
        return this;
    }

    public StringFlag setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public @NotNull List<String> getAliases() {
        return this.aliases;
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
    public String parse(@Nullable List<String> args) throws ParseArgumentException {
        if(args == null || args.size() == 0) {
            return this.defaultValue;
        }
        return String.join(" ", args);
    }

    @Override
    public List<String> tab(@Nullable List<String> args) {
        if(args.isEmpty()) return List.of();
        String input = args.getLast();
        if(input.isBlank()) {
            return List.of();
        }
        return List.of();
    }
}
