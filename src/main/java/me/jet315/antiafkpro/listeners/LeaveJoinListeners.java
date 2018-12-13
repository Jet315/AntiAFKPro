package me.jet315.antiafkpro.listeners;

import com.google.inject.Inject;
import me.jet315.antiafkpro.AntiAFKPro;
import me.jet315.antiafkpro.database.DataController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveJoinListeners implements Listener {

    @Inject
    private AntiAFKPro instance;
    @Inject
    private DataController dataController;

    public LeaveJoinListeners(){
        //this.instance = instance;
    }



    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        //load data
        dataController.asyncAddAFKPlayer(e.getPlayer());
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        //unload data
        dataController.asyncRemoveAFKPlayer(e.getPlayer());
    }




}
