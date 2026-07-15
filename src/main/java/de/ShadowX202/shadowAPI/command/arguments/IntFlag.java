package de.ShadowX202.shadowAPI.command.arguments;

import de.ShadowX202.shadowAPI.command.exception.ParseArgumentException;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Flag;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntFlag implements Flag<Integer> {
    private List<String> aliases;
    private String name;
    private Integer min;
    private Integer max;
    private boolean optional;
    private Integer defaultValue;

    public IntFlag(String name, String ...aliases) {
        this.name = name;
        this.aliases = Arrays.asList(aliases);
    }

    public IntFlag setMin(int min) {
        this.min = min;
        return this;
    }
    public IntFlag setMax(int max) {
        this.max = max;
        return this;
    }
    public IntFlag setOptional(boolean optional) {
        this.optional = optional;
        return this;
    }
    public IntFlag setDefaultValue(Integer defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public @NotNull List<String> getAliases() {
        return aliases;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isOptional() {
        return this.optional;
    }

    @Override
    public Integer parse(@org.jetbrains.annotations.Nullable List<String> args) throws ParseArgumentException {
        if(args == null  || args.size() == 0) {
            return this.defaultValue;
        }
        Integer num;
        try{
            num = Integer.parseInt(args.get(0));
        }catch(NumberFormatException e){
            throw new ParseArgumentException("Argument " + getName() + " is invalid");
        }
        if(this.min != null && num < this.min) {
            throw new ParseArgumentException("Argument " + getName() + " is too small");
        }
        if(this.max != null && num > this.max) {
            throw new ParseArgumentException("Argument " + getName() + " is too large");
        }
        return num;
    }

    @Override
    public List<String> tab(@Nullable @org.jetbrains.annotations.Nullable List<String> args) {
        List<String> list = new ArrayList<>();
        if(args.size() > 1) return list;
        if(this.min != null) list.add(this.min.toString());
        if(this.max != null) list.add(this.max.toString());
        if(args.size() != 1) return list;
        try{
            Integer num = Integer.parseInt(args.get(0));
            list.add(num.toString());
        }catch(NumberFormatException e){}
        return list;
    }
}
