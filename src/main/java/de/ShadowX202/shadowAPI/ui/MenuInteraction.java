package de.ShadowX202.shadowAPI.ui;

import de.ShadowX202.shadowAPI.ui.interfaces.Button;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuInteraction implements Listener {

    private final Menu menu;
    private final Inventory inventory;

    public MenuInteraction(Menu menu, Inventory inventory) {
        this.menu = menu;
        this.inventory = inventory;
    }

    @EventHandler(ignoreCancelled = true)
    public void interaction(InventoryClickEvent event) {
        Inventory inv = event.getView().getTopInventory();
        Inventory invBottom = event.getView().getBottomInventory();
        if(inv != this.inventory && invBottom != this.inventory ) return;
        event.setCancelled(true);
        if(inv != this.inventory) return;

        ClickType clickType = event.getClick();
        int slot = event.getSlot();
        Button but = menu.getButton(slot);
        HumanEntity human = event.getWhoClicked();

        if(but == null) return;
        if(!(human instanceof Player)) return;

        Player player = (Player) human;
        but.onClick(player, menu, clickType);
    }


    @EventHandler(ignoreCancelled = true)
    public void closeInventory(InventoryCloseEvent event) {
        if(event.getInventory() != this.inventory) return;
        HandlerList.unregisterAll(this);
    }
}
