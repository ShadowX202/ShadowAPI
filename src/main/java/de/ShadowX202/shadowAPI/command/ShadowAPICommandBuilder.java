package de.ShadowX202.shadowAPI.command;

import de.ShadowX202.shadowAPI.command.exception.MissingArgumentException;
import de.ShadowX202.shadowAPI.command.interfaces.CommandExecutor;
import de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommand;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Argument;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Flag;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ShadowAPICommandBuilder implements de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommandBuilder {

    private List<ShadowAPICommand> commands;
    private List<Flag> flags;
    private List<Argument> arguments;
    private CommandExecutor commandExecutor;
    private String name;
    private List<String> aliases;

    public ShadowAPICommandBuilder() {
        commands =  new ArrayList<>();
        flags = new ArrayList<>();
        arguments = new ArrayList<>();
        aliases = new ArrayList<>();
    }

    @Override
    public de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommandBuilder subCommand(ShadowAPICommand command) {
        commands.add(command);
        return this;
    }

    @Override
    public de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommandBuilder argument(Argument argument) {
        arguments.add(argument);
        return this;
    }

    @Override
    public de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommandBuilder flag(Flag flag) {
        flags.add(flag);
        return this;
    }

    @Override
    public de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommandBuilder executor(CommandExecutor executor) {
        commandExecutor = executor;
        return this;
    }

    @Override
    public de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommandBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommandBuilder alias(String alias) {
        aliases.add(alias);
        return this;
    }

    @Override
    public ShadowAPICommand build() {

        if(name == null || name.isEmpty()) {
            throw new RuntimeException("Name cannot be null or empty");
        }

        if(commandExecutor == null) {
            throw new RuntimeException("CommandExecutor cannot be null");
        }

        return new ShadowAPICommand() {
            @Override
            public List<ShadowAPICommand> getCommands() {
                return commands;
            }

            @Override
            public List<Flag> getFlags() {
                return flags;
            }

            @Override
            public List<Argument> getArguments() {
                return arguments;
            }

            @Override
            public CommandExecutor getCommandExecutor() {
                return commandExecutor;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public List<String> getAliases() {
                return aliases;
            }

            @Override
            public List<String> tab(@Nullable List<String> args) {
                return List.of(new String[]{name});
            }
        };
    }
}
