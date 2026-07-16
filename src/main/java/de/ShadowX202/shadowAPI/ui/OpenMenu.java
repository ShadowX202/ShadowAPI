package de.ShadowX202.shadowAPI.ui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class OpenMenu {

    private Inventory inventory;
    private Menu menu;
    private InventoryView view;

    public OpenMenu(Menu menu, Inventory inventory, InventoryView view) {
        this.menu = menu;
        this.inventory = inventory;
        this.view = view;
    }
    public Inventory getInventory() {
        return inventory;
    }
    public Menu getMenu() {
        return menu;
    }
    public InventoryView getView() {
        return view;
    }
}
