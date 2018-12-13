package me.jet315.antiafkpro.listeners;

import com.google.inject.Inject;
import me.jet315.antiafkpro.AntiAFKPro;
import me.jet315.antiafkpro.database.DataController;
import me.jet315.antiafkpro.gui.GUIController;
import me.jet315.antiafkpro.gui.GUIItem;
import me.jet315.antiafkpro.properties.GUIProperties;
import me.jet315.antiafkpro.properties.Properties;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class InventoryListeners implements Listener {

    @Inject
    private AntiAFKPro instance;
    @Inject
    private GUIController guiController;
    @Inject
    private GUIProperties guiProperties;


    @EventHandler
    public void onClickEvent(InventoryClickEvent e) {
        if (e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().equals(guiProperties.getInventoryName())) {
            if (e.getCurrentItem() != null) {
                for (GUIItem item : guiController.getGUIItems()) {
                    if (item.isItemEqual(e.getCurrentItem(), e.getRawSlot())) {
                        if (item.getCommand() != null && item.getCommand().length() > 0) {
                            if (item.getCommand().equalsIgnoreCase("%CLOSE%")) {
                                e.getWhoClicked().closeInventory();
                            } else {
                                ((Player) e.getWhoClicked()).performCommand(item.getCommand());
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCloseEvent(InventoryCloseEvent e) {
        guiController.removeActiveInventory((Player) e.getPlayer());
    }
}




