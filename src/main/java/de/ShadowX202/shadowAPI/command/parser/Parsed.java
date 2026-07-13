package de.ShadowX202.shadowAPI.command.parser;

import de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommand;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Argument;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Flag;

import java.util.HashMap;
import java.util.Map;

public class Parsed {

    private Map<Flag, Object> flags;
    private Map<Argument, Object> arguments;
    private ShadowAPICommand subCommand;
    private Parsed subParsed;

    public Parsed() {
        flags = new HashMap<>();
        arguments = new HashMap<>();

        subCommand = null;
        subParsed = null;
    }

    public void addArgument(Argument argument, Object value) {
        this.arguments.put(argument, value);
    }

    public void addFlag(Flag flag, Object value) {
        this.flags.put(flag, value);
    }

    public void addParsed(ShadowAPICommand command, Parsed parsed) {
        this.subCommand = command;
        this.subParsed = parsed;
    }

    public Map<Argument, Object> getArguments() {
        return arguments;
    }

    public Map<Flag, Object> getFlags() {
        return flags;
    }

    public ShadowAPICommand getSubCommand() {
        return subCommand;
    }

    public Parsed getSubParsed() {
        return subParsed;
    }
}
