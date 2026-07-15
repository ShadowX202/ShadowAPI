package de.ShadowX202.shadowAPI.command.parser;

import de.ShadowX202.shadowAPI.command.exception.MissingArgumentException;
import de.ShadowX202.shadowAPI.command.exception.ParseArgumentException;
import de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommand;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Argument;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Flag;
import org.bukkit.Bukkit;

import java.util.*;

public class Parser {

    public static class ArgumentValue {
        private final Argument argument;
        private final List<String> values;
        private boolean used = true;
        public ArgumentValue(Argument argument) {
            this.argument = argument;
            this.values = new ArrayList<>();
        }

        public ArgumentValue notUsed() {
            this.used = false;
            return this;
        }
        public ArgumentValue addValue(String value){
            values.add(value);
            return this;
        }
        public List<String> getValues() {
            return values;
        }
        public boolean isUsed() {
            return used;
        }
        public Argument getArgument() {
            return argument;
        }
    }

    public List<ArgumentValue> getArgumentValues(ShadowAPICommand command, String[] args){
        List<ArgumentValue> values = new ArrayList<>();
        ArgumentValue flag = null;

        for(int i = 0; i < args.length; i++){
            String arg = args[i];
            Argument argument = null;

            if(i < command.getArguments().size()){
                argument = command.getArguments().get(i);
                if(!(argument.isOptional() && arg.startsWith("-"))){
                    values.add(new ArgumentValue(argument).addValue(arg));
                    continue;
                }
            }

            if(arg.startsWith("-")){
                for(Flag f: command.getFlags()){
                    if(matchFlag(arg, f)){
                        if(flag != null){
                            values.add(flag);
                        }
                        flag = new ArgumentValue(f);
                    }
                }
            }else if(flag != null){
                flag.addValue(arg);
            }

        }

        if(flag != null){
            values.add(flag);
        }

        return  values;
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

        for(ShadowAPICommand subcommand : command.getCommands()) {
            if(quoted.length > 0 && matchCommand(quoted[0], subcommand)) {
                out.addParsed(subcommand, parse(subcommand, Arrays.copyOfRange(strings, 1, strings.length)));
                return out;
            }
        }

        List<ArgumentValue> argumentValues = getArgumentValues(command, quoted);

        List<Argument> allArgs = new ArrayList<>();
        allArgs.addAll(command.getArguments());
        allArgs.addAll(command.getFlags());

        for(Argument argument : allArgs) {
            boolean argFound = false;
            for(ArgumentValue argumentValue : argumentValues) {
                if(argumentValue.getArgument() == argument) {
                    argFound = true;
                    break;
                }
            }
            if(argFound) {
                continue;
            }
            if(!argument.isOptional()) {
                throw new MissingArgumentException(argument);
            }
            argumentValues.add(new ArgumentValue(argument).notUsed());
        }

        for(ArgumentValue argumentValue : argumentValues) {
            Argument argument = argumentValue.getArgument();
            List<String> values = null;
            if(argumentValue.isUsed()) {
                values = argumentValue.getValues();
            }

            if(argument instanceof Flag) {
                out.addFlag((Flag)argument, argument.parse(values) );
                continue;
            }
            out.addArgument(argument, argument.parse(values) );
        }

        return out;
    }
}
