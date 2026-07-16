package de.ShadowX202.shadowAPI.ui;

import de.ShadowX202.shadowAPI.Manager;
import de.ShadowX202.shadowAPI.ui.interfaces.Button;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Menu {

    protected String title;
    protected Map<Integer, Button> buttons;
    protected ItemStack backGround;
    protected InventorySize size;

    public enum InventorySize{
        SMALL(27),
        LARGE(54);

        InventorySize(int size) {
            this.size = size;
        }

        private int size;
        public int getSize() {
            return size;
        }
    }

    public Menu(String title, InventorySize size) {
        this.size = size;
        this.title = title;
        this.buttons = new HashMap<>();
    }

    protected Inventory createInventory(){
        Inventory inventory = Bukkit.createInventory(null, size.getSize(), title);
        for(Map.Entry<Integer, Button> entry : buttons.entrySet()){
            inventory.setItem(entry.getKey(), entry.getValue().getItem());
        }
        return inventory;
    }

    public @Nullable Button getButton(int slot){
        return buttons.get(slot);
    }

    public Menu setButton(int slot, Button button){
        buttons.put(slot, button);
        return this;
    }

    @Override
    public void show(Player user) {
        Inventory inventory = createInventory();
        user.openInventory(inventory);
        Bukkit.getServer().getPluginManager().registerEvents(new MenuInteraction(this, inventory), Manager.getPlugin());
    }
}
