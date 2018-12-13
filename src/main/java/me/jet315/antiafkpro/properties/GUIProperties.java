package me.jet315.antiafkpro.properties;

import me.jet315.antiafkpro.AntiAFKPro;
import me.jet315.antiafkpro.reflection.ReflectionUtils;
import me.jet315.antiafkpro.reflection.ServerVersion;
import me.jet315.antiafkpro.gui.GUIItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GUIProperties extends DataFile {


    private int inventorySlots = 9;
    private String inventoryName = "Play Time Menu";

    private ArrayList<GUIItem> inventoryItems = new ArrayList<>();

    public GUIProperties(String configName, AntiAFKPro instance) {
        super(configName, instance);
    }

    /**
     * Loads the config.yml
     */
    public void enable() {
        loadProperties();
    }


    private void loadProperties() {
        /**
         * Load display names
         */
        inventoryName = ChatColor.translateAlternateColorCodes('&', super.getConfig().getString("playtime_gui.gui_name"));
        inventorySlots = super.getConfig().getInt("playtime_gui.slots");

        for (String itemName : super.getConfig().getConfigurationSection("playtime_gui.items").getKeys(false)) {
            try {
                String path = "playtime_gui.items." + itemName;

                Material material = Material.valueOf(super.getConfig().getString(path + ".type"));

                if (material != null) {
                    ItemStack item;
                    if(ReflectionUtils.serverVersion == ServerVersion.VERSION1_13){
                        item = new ItemStack(material,1);
                    }else{
                        item = new ItemStack(material, 1, (short) super.getConfig().getInt(path + ".data"));
                    }

                    String displayName = ChatColor.translateAlternateColorCodes('&', super.getConfig().getString(path + ".name"));

                    int slotID = super.getConfig().getInt(path + ".slot");

                    List<String> lore = super.getConfig().getStringList(path + ".lore");

                    List<String> formattedLore = new ArrayList<>();
                    if (lore != null && lore.size() > 0) {

                        for (String loreLine : lore) {
                            formattedLore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
                        }
                    }
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.setDisplayName(displayName);
                    itemMeta.setLore(formattedLore);
                    item.setItemMeta(itemMeta);
                    GUIItem houseItem = new GUIItem(item, super.getConfig().getString(path + ".command_to_perform"), slotID);
                    inventoryItems.add(houseItem);
                } else {
                    System.out.println("An error has occured while loading an item:");
                    System.out.println("File: timeplayedgui.yml, (the material type is invalid for the item " + itemName + ")");
                    continue;
                }

            } catch (Exception e) {
                System.out.println("An error has occured while loading an item:");
                System.out.println("File: timeplayedgui.yml, on item: " + itemName);
                System.out.println("Details about the error: " + e);
            }

        }

    }

    public int getInventorySlots() {
        return inventorySlots;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public ArrayList<GUIItem> getInventoryItems() {
        return inventoryItems;
    }
}