package de.ShadowX202.shadowAPI;

import de.ShadowX202.shadowAPI.command.CommandManager;
import de.ShadowX202.shadowAPI.command.ShadowAPICommandBuilder;
import de.ShadowX202.shadowAPI.command.arguments.IntFlag;
import de.ShadowX202.shadowAPI.command.arguments.StringFlag;
import de.ShadowX202.shadowAPI.ui.Menu;
import de.ShadowX202.shadowAPI.ui.interfaces.Button;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.List;

public final class ShadowAPI extends JavaPlugin {

    private static ShadowAPI instance;

    @Override
    public void onEnable() {
        this.instance = this;
        Manager.setPlugin(this);
        CommandManager cm = new CommandManager();
        cm.registerCommand(
                new ShadowAPICommandBuilder()
                        .name("echo")
                        .flag(new StringFlag("message", "m", "msg"))
                        .flag(new IntFlag("amount", "count", "a", "c")
                                .setDefaultValue(1)
                                .setOptional(true)
                                .setMin(1)
                        )
                        .executor(context -> {
                            Integer amount = (Integer) context.getFlag("amount");
                            if(!context.isPlayer()) return true;
                            for(int i = 0; i < amount; i++) {
                                context.getPlayer().sendMessage((String)context.getFlag("message"));
                            }
                            return true;
                        })
                        .build()
        );

        cm.registerCommand(
                new ShadowAPICommandBuilder()
                        .name("broadcast")
                        .subCommand(
                                new ShadowAPICommandBuilder()
                                        .name("send")
                                        .flag(new StringFlag("message", "m", "msg"))
                                        .executor((ctx) -> {
                                            Bukkit.getServer().broadcastMessage((String)ctx.getFlag("message"));
                                            return true;
                                        })
                                        .build()
                        )
                        .executor((ctx) -> {
                            return false;
                        })
                        .build()
        );

        Menu menu = new Menu("Menu", Menu.InventorySize.LARGE);
        menu.setButton(0, new Button() {
            @Override
            public void onClick(Player user, Menu menu, ClickType clickType) {
                user.sendMessage("Hi");
            }

            @Override
            public ItemStack getItem() {
                return new ItemStack(Material.EMERALD);
            }
        });

        cm.registerCommand(
                new ShadowAPICommandBuilder()
                        .name("menu")
                        .executor((ctx) -> {
                            if(!ctx.isPlayer()) return false;
                            menu.show(ctx.getPlayer());
                            return true;
                        })
                        .build()
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
