package de.ShadowX202.shadowAPI.command.parser;

import de.ShadowX202.shadowAPI.command.exception.MissingArgumentException;
import de.ShadowX202.shadowAPI.command.exception.ParseArgumentException;
import de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommand;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Argument;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Flag;
import org.bukkit.Bukkit;

import java.util.*;

public class Parser {

    private Flag findFlag(List<Flag> commandFlags, String string){
        while(string.startsWith("-")){
            string = string.substring(1);
        }
        for(Flag flag : commandFlags){
            if(flag.getName().equalsIgnoreCase(string)){
                return flag;
            }
            List<String> aliases = flag.getAliases();
            for(String alias : aliases){
                if(alias.equalsIgnoreCase(string)){
                    return flag;
                }
            }
        }
        return null;
    }

    public String[] mergeQuotedArguments(String[] args) {
        return this.mergeQuotedArguments(args, false);
    }

    public String[] mergeQuotedArguments(String[] args, boolean ignoreNotClosedQuotes) throws ParseArgumentException {
        List<String> result = new ArrayList<>();
        StringBuilder current = null;

        for (String arg : args) {
            boolean startsQuote = arg.startsWith("\"");
            boolean endsQuote = arg.endsWith("\"");
            arg = arg.replace("\"", "");

            if (current != null) {
                current.append(' ').append(arg);

                if (endsQuote) {
                    result.add(current.toString());
                    current = null;
                }
            } else if (startsQuote && !endsQuote) {
                current = new StringBuilder(arg);
            } else {
                result.add(arg);
            }
        }

        if (current != null && !ignoreNotClosedQuotes) {
            throw new ParseArgumentException("Quotes not closed");
        }

        if(current!=null){
            result.add(current.toString());
        }

        String[] out = result.toArray(new String[result.size()]);
        return out;
    }

    protected boolean matchFlag(String input, Flag flag) {
        List<String> aliases = flag.getAliases();
        if (input.toLowerCase().equals("--" + flag.getName().toLowerCase())) {
            return true;
        }
        for (String alias : aliases) {
            if (input.toLowerCase().equals("-" + alias.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    protected boolean matchCommand(String input, ShadowAPICommand command) {
        if(input.toLowerCase().equals(command.getName().toLowerCase())) {
            return true;
        }
        for(String alias : command.getAliases()) {
            if (input.toLowerCase().equals(alias.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public Parsed parse(ShadowAPICommand command, String[] strings) throws MissingArgumentException, ParseArgumentException {

        Parsed out = new Parsed();

        String[] quoted = this.mergeQuotedArguments(strings);

        for(ShadowAPICommand subCommand : command.getCommands() ) {
            if(quoted.length >= 1 && matchCommand(quoted[0], subCommand)) {
                out.addParsed(subCommand, parse(subCommand, Arrays.copyOfRange(quoted, 1, quoted.length)));
                return out;
            }
        }

        int index = 0;
        for (Argument argument : command.getArguments()) {
            if (index >= quoted.length) {
                if (argument.isOptional()){
                    out.addArgument(argument, argument.parse(strings, null));
                    continue;
                }
                throw new MissingArgumentException("Missing argument: "+ argument.getName());
            }
            out.addArgument(argument, argument.parse(quoted, index));
            index++;
        }

        for (Flag flag : command.getFlags()) {
            boolean flagFound = false;
            for (int i = index; i < quoted.length; i++) {
                if(matchFlag(quoted[i], flag)){
                    flagFound = true;
                    Object obj = flag.parse(quoted, i+1);
                    out.addFlag(flag, obj);
                    break;
                }
            }

            if(!flagFound){
                if(!flag.isOptional()){
                    throw new ParseArgumentException("Missing flag: " + flag.getName());
                }
                out.addFlag(flag, flag.parse(strings, null));
            };
        }

        return out;
    }
}
