package de.ShadowX202.shadowAPI.command.arguments;

import de.ShadowX202.shadowAPI.command.exception.ParseArgumentException;
import de.ShadowX202.shadowAPI.command.interfaces.argument.Flag;
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
    public Integer parse(String[] args, @org.jetbrains.annotations.Nullable Integer index) throws ParseArgumentException {
        if(index == null) {
            return this.defaultValue;
        }
        Integer num;
        try{
            num = Integer.parseInt(args[index]);
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
    public List<String> tab(@Nullable @org.jetbrains.annotations.Nullable String[] args, @org.jetbrains.annotations.Nullable Integer index) {
        List<String> list = new ArrayList<>();
        if(this.min != null) list.add(this.min.toString());
        if(this.max != null) list.add(this.max.toString());
        return list;
    }
}
