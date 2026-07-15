package de.ShadowX202.shadowAPI;

import de.ShadowX202.shadowAPI.command.ShadowAPICommandBuilder;
import de.ShadowX202.shadowAPI.command.arguments.StringFlag;
import de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommand;
import de.ShadowX202.shadowAPI.command.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        String[] str = "hi --flag2 hi --flag \"hi ho\"     ".split(" ");
        String[] quoted = parser.mergeQuotedArguments(str);

        ShadowAPICommand cmd = new ShadowAPICommandBuilder()
                .name("test")
                .argument(
                        new StringFlag("arg"))
                .flag(new StringFlag("flag"))
                .flag(new StringFlag("flag2"))
                .executor((ctx) -> false)
                .build();

        List<Parser.ArgumentValue> li = parser.getArgumentValues(cmd, quoted);

        for (Parser.ArgumentValue v : li) {
            System.out.println(v.getArgument().getName());
            for (String value: v.getValues()) {
                System.out.println(" | >" + value);
            }
        }
    }
}
