package me.jet315.antiafkpro.tasks;

import com.google.inject.Inject;
import me.jet315.antiafkpro.AntiAFKPro;
import me.jet315.antiafkpro.gui.GUIController;
import me.jet315.antiafkpro.manager.AFKPlayer;
import me.jet315.antiafkpro.manager.ActionController;
import me.jet315.antiafkpro.manager.PlayerController;
import me.jet315.antiafkpro.properties.Properties;
import me.jet315.antiafkpro.utils.MouseLocation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class AFKAsyncTask extends BukkitRunnable {

    @Inject
    private AntiAFKPro plugin;
    @Inject
    private PlayerController playerController;
    @Inject
    private ActionController actionController;
    @Inject
    private GUIController guiController;
    @Inject
    private Properties properties;

    /**
     * Starts the process
     */
    public void enable() {
        this.runTaskTimerAsynchronously(plugin, 20, properties.getCheckDelay() * 20);
    }


    public void run() {
        //run afk timer for all online players
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID uuid = p.getUniqueId();
            AFKPlayer afkPlayer = playerController.getOnlinePlayers().get(uuid);
            //check if player exists
            if (afkPlayer != null) {
                //exists, so update the player

                //update player time
                afkPlayer.setSecondsPlayed(afkPlayer.getSecondsPlayed() + properties.getCheckDelay());

                //Returns if player is AFK
                if (updateAFKPlayer(afkPlayer)) {
                    //player is AFK
                    actionController.checkPlayerAgainstNodes(afkPlayer);

                }
            }
        }
        guiController.refreshPlayerTimeInventories();

    }


    /**
     * @param afkPlayer The AFKPlayer object
     * @return True if the mouse location was the same as the last time it was checked
     */
    private boolean updateAFKPlayer(AFKPlayer afkPlayer) {
        //if(afkPlayer != null){ // null check is already done
        MouseLocation currentMouseLoc = new MouseLocation(afkPlayer.getPlayer());
        MouseLocation oldMouseLoc = afkPlayer.getLastMouseLocation();

        //update last players loc & time
        afkPlayer.setLastMouseLocation(currentMouseLoc);

        if (oldMouseLoc != null) {
            if (currentMouseLoc.isMouseLocationEqual(oldMouseLoc)) {
                //afk
                afkPlayer.setSecondsAFK(afkPlayer.getSecondsAFK() + properties.getCheckDelay());
                return true;
            }
        }

        // }
        afkPlayer.setSecondsAFK(0);
        return false;

    }
}

