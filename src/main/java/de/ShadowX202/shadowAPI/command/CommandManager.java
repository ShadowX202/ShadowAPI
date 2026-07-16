package de.ShadowX202.shadowAPI.command;

import de.ShadowX202.shadowAPI.Manager;
import de.ShadowX202.shadowAPI.command.adapter.ExecutorAdapter;
import de.ShadowX202.shadowAPI.command.adapter.TabCompleteAdapter;
import de.ShadowX202.shadowAPI.command.interfaces.ShadowAPICommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class CommandManager implements de.ShadowX202.shadowAPI.command.interfaces.CommandManager {

    private Set<ShadowAPICommand> commands;
    private static CommandManager instance;
    private JavaPlugin plugin;

    public CommandManager() {
        this.commands = new HashSet<>();
        this.plugin = Manager.getPlugin();
    }

    @Override
    public boolean registerCommand(ShadowAPICommand command) {
        PluginCommand cmd = plugin.getCommand(command.getName());
        cmd.setExecutor(new ExecutorAdapter(command));
        cmd.setTabCompleter(new TabCompleteAdapter(command));
        return commands.add(command);
    }

    @Override
    public boolean unregisterCommand(ShadowAPICommand command) {
        PluginCommand cmd = plugin.getCommand(command.getName());
        cmd.setExecutor(null);
        cmd.setTabCompleter(null);
        return commands.remove(command);
    }

}
