package de.ShadowX202.shadowAPI;

import de.ShadowX202.shadowAPI.command.parser.Parser;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        String[] str = "echo -p \"Shadow   X202\" -m \"Message\" ka    ha".split(" ");
        System.out.println(String.join("; ", parser.mergeQuotedArguments(str )));
    }
}
