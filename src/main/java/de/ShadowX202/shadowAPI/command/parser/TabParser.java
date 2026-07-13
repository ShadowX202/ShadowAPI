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

    public List<Tabable> parseTab(ShadowAPICommand command, String[] args) {
        List<Tabable> result = new ArrayList<Tabable>();

        String[] quoted = this.mergeQuotedArguments(args, true);

        int index = 0;
        if(quoted.length >= 1){
            index = quoted.length - 1;
            if(quoted[quoted.length - 1].isBlank()){
                index++;
            }
        }

        if(!command.getCommands().isEmpty() && quoted.length <= 1) {
            result.addAll(command.getCommands());
        }
        if(!command.getCommands().isEmpty() && quoted.length > 1) {
            for(ShadowAPICommand command2: command.getCommands()) {
                if (this.matchCommand(quoted[0], command2)) {
                    return parseTab(command2, Arrays.copyOfRange(args, 1, args.length));
                }
            }
        }

        Argument arg = null;
        if(command.getArguments().size() > index){
            arg = command.getArguments().get(index);
            Argument finalArg = arg;
            result.add((strings, integer) -> List.of(new String[]{finalArg.getName()}));
            result.add(arg);
            int i = index;
            while(arg.isOptional() && i < quoted.length){
                arg = command.getArguments().get(i++);
                Argument finalArg2 = arg;
                result.add((strings, integer) -> List.of(new String[]{finalArg2.getName()}));;
                result.add(arg);
            }
        }

        if(arg == null || arg.isOptional()){
            Flag activeFlag = activeFlag(command, quoted);
            if(activeFlag == null){
                for(Flag flag : command.getFlags()){
                    if(result.contains(flag)){}

                    result.add((unusedArgs, unusedIndex) -> {
                        List<String> flagResult = new ArrayList<>();
                        flagResult.add("--"+flag.getName());
                        List<String> aliases = flag.getAliases();
                        for(String alias: aliases){
                            flagResult.add("-"+alias);
                        }
                        return flagResult;
                    });

                }
            }else{
                result.add(activeFlag);
            }

        }

        return result;
    }
}
