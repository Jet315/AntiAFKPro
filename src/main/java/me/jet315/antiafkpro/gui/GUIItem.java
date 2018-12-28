package me.jet315.antiafkpro.gui;

import org.bukkit.inventory.ItemStack;

public class GUIItem {

    private ItemStack item;
    private String command;
    private int slotID;
    private boolean isTimeSlot = false;

    public GUIItem( ItemStack item, String command, int slotID) {
        this.item = item;
        this.command = command;
        this.slotID = slotID;
        if(item.getItemMeta().getDisplayName().contains("%")){
            isTimeSlot = true;
        }
        //not perfect but efficient & works
        if(item.getItemMeta().hasLore()){
            for(String s : item.getItemMeta().getLore()){
                if(s.contains("%TIME")){
                    isTimeSlot = true;
                    break;
                }
            }
        }
    }



    public ItemStack getItem() {
        return item;
    }

    public int getSlotID() {
        return slotID;
    }

    /**
     * Method to check whether the item being parsed in is equal to the item in stored in this class
     */
    public boolean isItemEqual(ItemStack possibleItem) {
        if (possibleItem.getType() == item.getType()) {
            if (possibleItem.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param possibleItem The item in question
     * @param slotClicked The slot the player clicked on the GUI
     * @return A more accurate version of isItemEqual
     */
    public boolean isItemEqual(ItemStack possibleItem, int slotClicked) {

        if (possibleItem.getType() == item.getType() && slotClicked == this.slotID) {
            return true;
        }
        return false;
    }

    public String getCommand() {
        return command;
    }

    public boolean isTimeSlot() {
        return isTimeSlot;
    }
}
