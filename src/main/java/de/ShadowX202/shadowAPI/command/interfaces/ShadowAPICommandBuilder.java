package de.ShadowX202.shadowAPI.command.interfaces;

import de.ShadowX202.shadowAPI.command.interfaces.argument.Argument;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Flag;

public interface ShadowAPICommandBuilder {


    ShadowAPICommandBuilder name(String name);
    ShadowAPICommandBuilder alias(String alias);
    ShadowAPICommandBuilder subCommand(ShadowAPICommand command);
    ShadowAPICommandBuilder argument(Argument argument);
    ShadowAPICommandBuilder flag(Flag flag);
    ShadowAPICommandBuilder executor(CommandExecutor executor);

    ShadowAPICommand build();
}
