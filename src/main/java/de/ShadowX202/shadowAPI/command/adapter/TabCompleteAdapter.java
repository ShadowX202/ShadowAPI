package de.ShadowX202.shadowAPI.command.adapter;

import de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommand;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Argument;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Flag;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Tabable;
import de.ShadowX202.shadowAPI.command.parser.Parser;
import de.ShadowX202.shadowAPI.command.parser.TabParser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class TabCompleteAdapter implements TabCompleter {

    private final ShadowAPICommand command;
    private final TabParser tabParser;

    public TabCompleteAdapter(ShadowAPICommand command) {
        this.command = command;
        this.tabParser = new TabParser();
    }


    private List<String> executeTab(ShadowAPICommand command, String[] args) {
        List<String> result = new ArrayList<>();

        String[] quoted = tabParser.mergeQuotedArguments(args, true);
        List<Tabable> possible = tabParser.parseTab(command, args);

        for(Tabable tabable : possible) {
            for(String str: tabable.tab(args, quoted.length-1)){
                String lowStr = str.toLowerCase();
                if(quoted.length == 0 || lowStr.startsWith(quoted[quoted.length-1].toLowerCase()) || quoted[quoted.length-1].isBlank()) {
                    result.add(lowStr);
                }
            }
        }

        return result;
    }

    @Override
    public List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String alias,
            @NotNull String[] args
    ) {
        return executeTab(this.command, args);
    }
}