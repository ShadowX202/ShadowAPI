package de.ShadowX202.shadowAPI.command.interfaces;

import de.ShadowX202.shadowAPI.command.interfaces.argument.Argument;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Flag;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Tabable;

import java.util.List;

public interface ShadowAPICommand extends Tabable {
    List<ShadowAPICommand> getCommands();
    List<Flag> getFlags();
    List<Argument> getArguments();
    CommandExecutor getCommandExecutor();
    String getName();
    List<String> getAliases();
}
