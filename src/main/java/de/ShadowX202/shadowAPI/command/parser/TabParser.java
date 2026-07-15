package de.ShadowX202.shadowAPI.command.parser;

import de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommand;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Argument;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Flag;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Tabable;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class TabParser extends Parser{

    public Flag activeFlag(ShadowAPICommand command, String[] args){
        if(args.length >= 2){
            String flagStr = args[args.length-2];
            for(Flag flag: command.getFlags()){
                if(matchFlag(flagStr,flag)){
                    return flag;
                }
            }
        }
        return null;
    }

    public List<String> parseTab(ShadowAPICommand command, String[] args) {
        List<String> result = new ArrayList<String>();

        String[] quoted = this.mergeQuotedArguments(args, true);

        if(!command.getCommands().isEmpty() && quoted.length == 1) {
            for(ShadowAPICommand command2: command.getCommands()){
                result.add(command2.getName());
            }
        }
        if(!command.getCommands().isEmpty() && quoted.length > 1) {
            for(ShadowAPICommand command2: command.getCommands()) {
                if (this.matchCommand(quoted[0], command2)) {
                    return parseTab(command2, Arrays.copyOfRange(args, 1, args.length));
                }
            }
        }

        List<ArgumentValue> argumentValues = this.getArgumentValues(command, quoted);
        int index = argumentValues.size();
        if(index < command.getArguments().size()) {
            Argument argument = command.getArguments().get(index);
            result.addAll(argument.tab(argumentValues.get(index).getValues()));
            if(!argument.isOptional()) return result;
        }


        if(!argumentValues.isEmpty()){
            ArgumentValue argumentValue = argumentValues.getLast();
            Argument argument = argumentValue.getArgument();
            if(argument instanceof Flag<?>){
                Flag flag = (Flag<?>)argument;
                List<String> values = argumentValue.getValues();
                List<String> flagTab = flag.tab(values);
                if(!flagTab.isEmpty()){
                    if(values.isEmpty() || !values.getLast().startsWith("-") ){
                        result.addAll(flagTab);
                        return result;
                    }
                }
            };

        }

        for(Flag flag: command.getFlags()){
            result.add("--"+flag.getName());
            List<String> aliases = flag.getAliases();
            for(String alias: aliases){
                result.add("-"+alias);
            }
        }

        return result;
    }
}
