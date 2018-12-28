package me.jet315.antiafkpro.properties;

import me.jet315.antiafkpro.AntiAFKPro;
import org.bukkit.ChatColor;

public class Messages extends DataFile{

    private String noPermission = "No permission";
    private String failedToLoadStats = "No stats";
    private String playtimeMessage = "Playtime";

    public Messages(String configName, AntiAFKPro instance) {
        super(configName, instance);
    }

    public void enable(){
        loadProperties();
    }

    public void disable(){

    }

    private void loadProperties() {
        noPermission = ChatColor.translateAlternateColorCodes('&',super.getConfig().getString("no_permission"));
        failedToLoadStats = ChatColor.translateAlternateColorCodes('&',super.getConfig().getString("failed_to_load_stats"));
        playtimeMessage = ChatColor.translateAlternateColorCodes('&',super.getConfig().getString("playtime_message"));
    }


    public String getNoPermission() {
        return noPermission;
    }

    public String getFailedToLoadStats() {
        return failedToLoadStats;
    }

    public String getPlaytimeMessage() {
        return playtimeMessage;
    }
}
