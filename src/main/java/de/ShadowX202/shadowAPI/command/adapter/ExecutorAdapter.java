package de.ShadowX202.shadowAPI.command.adapter;

import de.ShadowX202.shadowAPI.command.CommandContext;
import de.ShadowX202.shadowAPI.command.exception.MissingArgumentException;
import de.ShadowX202.shadowAPI.command.exception.ParseArgumentException;
import de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommand;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Argument;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Flag;
import de.ShadowX202.shadowAPI.command.parser.Parsed;
import de.ShadowX202.shadowAPI.command.parser.Parser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExecutorAdapter implements CommandExecutor {
    private final ShadowAPICommand command;
    private final Parser parser = new Parser();

    public ExecutorAdapter(ShadowAPICommand command) {
        this.command = command;
    }

    public boolean executeCommand(ShadowAPICommand command, CommandSender sender, Parsed parsed) {
        if(parsed.getSubCommand() != null) {
            return executeCommand(parsed.getSubCommand(), sender, parsed.getSubParsed());
        }

        Map<String, Object> arguments = new HashMap<>();
        for(Map.Entry<Argument, Object> entry: parsed.getArguments().entrySet()){
            arguments.put(entry.getKey().getName(), entry.getValue());
        }

        Map<String, Object> flags = new HashMap<>();
        for(Map.Entry<Flag, Object> entry: parsed.getFlags().entrySet()){
            flags.put(entry.getKey().getName(), entry.getValue());
        }

        CommandContext context = new CommandContext(command, sender, arguments, flags);

        return command.getCommandExecutor().execute(context);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command mccommand, @NotNull String s, @NotNull String @NotNull [] strings) {
        Parsed parsed;
        try{
            parsed = parser.parse(command, strings);
        }catch (ParseArgumentException | MissingArgumentException e){
            commandSender.sendMessage("§c"+e.getMessage());
            return false;
        }

        return executeCommand(command, commandSender, parsed);
    }
}
