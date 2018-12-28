package me.jet315.antiafkpro;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.jet315.antiafkpro.commands.CommandHandler;
import me.jet315.antiafkpro.database.DataController;
import me.jet315.antiafkpro.listeners.InventoryListeners;
import me.jet315.antiafkpro.listeners.LeaveJoinListeners;
import me.jet315.antiafkpro.manager.ActionController;
import me.jet315.antiafkpro.manager.PlayerController;
import me.jet315.antiafkpro.properties.GUIProperties;
import me.jet315.antiafkpro.properties.Messages;
import me.jet315.antiafkpro.properties.Properties;
import me.jet315.antiafkpro.tasks.AFKAsyncTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiAFKPro extends JavaPlugin {

    private Injector injector;

    /**
     * Injected classes
     */
    @Inject private Properties properties;
    @Inject private GUIProperties guiProperties;
    @Inject private Messages messages;
    @Inject private DataController dataController;
    @Inject private CommandHandler commandHandler;
    @Inject private PlayerController playerController;
    @Inject private AFKAsyncTask afkAsyncTask;
    @Inject private ActionController actionController;
    @Inject private LeaveJoinListeners leaveJoinListeners;
    @Inject private InventoryListeners inventoryListeners;

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();
        System.out.println("\n[AntiAFKPro] Initializing Plugin");

        // Guice
        AFKBinderModule module = new AFKBinderModule(this);
        injector = module.createInjector();
        injector.injectMembers(this);

        // Register commands
        this.getCommand("playtime").setExecutor(this.commandHandler);

        //Register Events
        getServer().getPluginManager().registerEvents(this.leaveJoinListeners,this);
        getServer().getPluginManager().registerEvents(this.inventoryListeners,this);

        //Property data
        properties.enable();
        guiProperties.enable();
        dataController.enable();
        messages.enable();
        //tasks
        afkAsyncTask.enable();

        //To enable plugman reloading
        loadOnlinePlayers();

        System.out.println("[Houses] Initializing Complete in " + String.valueOf(System.currentTimeMillis()-startTime) + " Ms\n");

    }

    @Override
    public void onDisable() {

    }

    public void reloadConfiguration(){
        messages.disable();
        guiProperties.disable();
        properties.disable();
        actionController.disable();

        actionController.enable();
        properties.enable();
        guiProperties.enable();
        messages.enable();
    }

    /**
     * Loads online players in the instance of a reload
     */
    private void loadOnlinePlayers(){
        for(Player p : Bukkit.getOnlinePlayers()){
            dataController.asyncAddAFKPlayer(p);
        }
    }

}
