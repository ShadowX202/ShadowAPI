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

    private final OpenMenu menu;

    public MenuInteraction(OpenMenu menu) {
        this.menu = menu;
    }

    @EventHandler(ignoreCancelled = true)
    public void interaction(InventoryClickEvent event) {
        Inventory inventory = this.menu.getInventory();
        Menu menu = this.menu.getMenu();

        Inventory inv = event.getView().getTopInventory();
        Inventory invBottom = event.getView().getBottomInventory();
        if(inv != inventory && invBottom != inventory ) return;
        event.setCancelled(true);
        if(inv != inventory) return;

        ClickType clickType = event.getClick();
        int slot = event.getSlot();
        Button but = menu.getButton(slot);
        HumanEntity human = event.getWhoClicked();

        if(but == null) return;
        if(!(human instanceof Player)) return;

        Player player = (Player) human;
        but.onClick(player, this.menu, clickType);
    }


    @EventHandler(ignoreCancelled = true)
    public void closeInventory(InventoryCloseEvent event) {
        if(event.getInventory() != this.menu.getInventory()) return;
        HandlerList.unregisterAll(this);
    }
}
