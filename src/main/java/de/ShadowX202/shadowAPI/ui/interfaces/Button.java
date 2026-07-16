package de.ShadowX202.shadowAPI.ui.interfaces;

import de.ShadowX202.shadowAPI.ui.Menu;
import de.ShadowX202.shadowAPI.ui.OpenMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface Button {
    void onClick(Player user, OpenMenu menu, ClickType clickType);
    ItemStack getItem();
}
