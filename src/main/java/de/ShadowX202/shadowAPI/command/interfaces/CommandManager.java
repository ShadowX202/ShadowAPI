package de.ShadowX202.shadowAPI.command.interfaces;

public interface CommandManager {
    boolean registerCommand(ShadowAPICommand command);
    boolean unregisterCommand(ShadowAPICommand command);
}
