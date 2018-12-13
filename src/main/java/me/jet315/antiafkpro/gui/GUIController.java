package me.jet315.antiafkpro.gui;

import com.google.inject.Inject;
import me.jet315.antiafkpro.AntiAFKPro;
import me.jet315.antiafkpro.listeners.InventoryListeners;
import me.jet315.antiafkpro.manager.AFKPlayer;
import me.jet315.antiafkpro.manager.PlayerController;
import me.jet315.antiafkpro.properties.GUIProperties;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class GUIController {

    @Inject
    private AntiAFKPro instance;
    @Inject
    private GUIProperties properties;
    @Inject
    private PlayerController playerController;
    private HashMap<AFKPlayer,Inventory> activeInventories = new HashMap<>();

    public GUIController(){

    }

    /**
     *
     * @param p The player requesting the inventory
     * @return A Player Time Inventory, null if it doesn't exist
     */
    public Inventory getTimeInventory(Player p){
        AFKPlayer afkPlayer = playerController.getAFKPlayer(p);
        if(afkPlayer == null){
            //failed to load inventory
            return null;
        }
        Inventory inventory = Bukkit.createInventory(p,properties.getInventorySlots(),properties.getInventoryName());
        int playtime[] = afkPlayer.getPlaytime(true);
        String days = String.valueOf(playtime[0]);
        String hours = String.valueOf(playtime[1]);
        String mins = String.valueOf(playtime[2]);
        String secs = String.valueOf(playtime[3]);
        String unformattedSecs = String.valueOf(afkPlayer.getSecondsPlayed());

        for(GUIItem inventoryItem : properties.getInventoryItems()){
            ItemStack itemClone = inventoryItem.getItem().clone();
            if(inventoryItem.isTimeSlot()){
                //format times
                ItemMeta itemMeta = itemClone.getItemMeta();
                itemMeta.setDisplayName(itemMeta.getDisplayName().replaceAll("%TIME_PLAYED_DAYS%",days).replaceAll("%TIME_PLAYED_HOURS%",hours).replaceAll("%TIME_PLAYED_MINUTES%",mins).replaceAll("%TIME_PLAYED_SECONDS%",secs).replaceAll("%TIME_PLAYED_SECONDS_UNFORMATTED%",unformattedSecs));

                if(itemMeta.hasLore()){
                    ArrayList<String> formattedLore = new ArrayList<>();
                    for(String s : itemMeta.getLore()){
                        formattedLore.add(s.replaceAll("%TIME_PLAYED_DAYS%",days).replaceAll("%TIME_PLAYED_HOURS%",hours).replaceAll("%TIME_PLAYED_MINUTES%",mins).replaceAll("%TIME_PLAYED_SECONDS%",secs).replaceAll("%TIME_PLAYED_SECONDS_UNFORMATTED%",unformattedSecs));
                    }
                    itemMeta.setLore(formattedLore);
                }
                itemClone.setItemMeta(itemMeta);
            }
            inventory.setItem(inventoryItem.getSlotID(),itemClone);

        }
        activeInventories.put(afkPlayer,inventory);
        return inventory;
    }

    /**
     * Called from an AsyncTask, hence the runnable
     */
    public void refreshPlayerTimeInventories(){
        if(activeInventories.size() == 0) return;
        Bukkit.getScheduler().runTask(instance, new Runnable() {
            @Override
            public void run() {
                for(AFKPlayer p : activeInventories.keySet()){
                    Inventory inv = activeInventories.get(p);

                    if(inv == null){
                        activeInventories.remove(p);
                        return;
                    }
                    int playtime[] = p.getPlaytime(true);
                    String days = String.valueOf(playtime[0]);
                    String hours = String.valueOf(playtime[1]);
                    String mins = String.valueOf(playtime[2]);
                    String secs = String.valueOf(playtime[3]);
                    String unformattedSecs = String.valueOf(p.getSecondsPlayed());
                    for(GUIItem itemToRefresh : properties.getInventoryItems()){
                        if(itemToRefresh.isTimeSlot()){
                            //needs updating
                            ItemStack itemClone = itemToRefresh.getItem().clone();
                            //format times
                            ItemMeta itemMeta = itemClone.getItemMeta();
                            itemMeta.setDisplayName(itemMeta.getDisplayName().replaceAll("%TIME_PLAYED_DAYS%",days).replaceAll("%TIME_PLAYED_HOURS%",hours).replaceAll("%TIME_PLAYED_MINUTES%",mins).replaceAll("%TIME_PLAYED_SECONDS%",secs).replaceAll("%TIME_PLAYED_SECONDS_UNFORMATTED%",unformattedSecs));

                            if(itemMeta.hasLore()){
                                ArrayList<String> formattedLore = new ArrayList<>();
                                for(String s : itemMeta.getLore()){
                                    formattedLore.add(s.replaceAll("%TIME_PLAYED_DAYS%",days).replaceAll("%TIME_PLAYED_HOURS%",hours).replaceAll("%TIME_PLAYED_MINUTES%",mins).replaceAll("%TIME_PLAYED_SECONDS%",secs).replaceAll("%TIME_PLAYED_SECONDS_UNFORMATTED%",unformattedSecs));
                                }
                                itemMeta.setLore(formattedLore);
                            }
                            itemClone.setItemMeta(itemMeta);
                            inv.setItem(itemToRefresh.getSlotID(),itemClone);
                        }

                    }
                }
            }
        });
    }

    /**
     * Removes a player from active inventory list
     * @param p Player
     */
    public void removeActiveInventory(Player p){
        for(AFKPlayer afkPlayer : activeInventories.keySet()){
            if(afkPlayer.getPlayersUUID() == p.getUniqueId()){
                activeInventories.remove(afkPlayer);
                break;
            }
        }
    }

    public ArrayList<GUIItem> getGUIItems(){
        return properties.getInventoryItems();
    }
}
