package me.jet315.antiafkpro.database;

import com.google.inject.Inject;
import me.jet315.antiafkpro.AntiAFKPro;
import me.jet315.antiafkpro.manager.AFKPlayer;
import me.jet315.antiafkpro.manager.PlayerController;
import me.jet315.antiafkpro.properties.Properties;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DataController {


    @Inject
    private Properties properties;
    @Inject
    private AntiAFKPro instance;
    @Inject
    private PlayerController playerController;

    private Database database;


    public DataController(){


    }

    public void enable(){
        initialise();
    }

    private void initialise(){
        if(properties.isStorePlayerTime()) {
            if (properties.getDatabaseType() == DatabaseType.MYSQL) {
                database = new MySQL(instance, properties, properties.getMySQLHost(), properties.getMySQLPort(), properties.getMySQLDB(), properties.getMySQLUser(), properties.getMySQLPassword());
            } else {
                database = new SQLite(instance, properties, properties.getSqliteDB());
            }
            database.initialise();
        }
    }

    /**
     * Asyncly adds a player to the AFKPlayers list so that the player will be checked for being AFK / PlayTime
     *
     * @param p
     */
    public void asyncAddAFKPlayer(Player p){
        UUID uuid = p.getUniqueId();
        if(p == null || !p.isOnline() || !properties.isStorePlayerTime()){
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(instance, new Runnable() {
            @Override
            public void run() {
                int result = database.queryPlayTime("SELECT * FROM player_afk_data WHERE uuid='" + uuid.toString() + "';");
                AFKPlayer afkPlayer = new AFKPlayer(p,uuid,result);
                playerController.addAFKPlayer(afkPlayer);
            }
        });
    }

    /**
     *
     * Asyncly removes a player from the AFKPlayers list so that the player will no longer be checked for being AFK / PlayTime
     *
     * @param p
     */
    public void asyncRemoveAFKPlayer(Player p){
        if(p == null || !properties.isStorePlayerTime()) return;

        AFKPlayer afkPlayer = playerController.getOnlinePlayers().get(p.getUniqueId());
        if(afkPlayer == null) return;
        int timePlayed = afkPlayer.getSecondsPlayed();
        UUID uuid = p.getUniqueId();
        playerController.removeAFKPlayer(afkPlayer);

        Bukkit.getScheduler().runTaskAsynchronously(instance, new Runnable() {
            @Override
            public void run() {
                //database.query("UPDATE player_afk_data SET playtime='" + String.valueOf(timePlayed) + "' WHERE uuid='" + uuid.toString() + "';");
                database.queryVoid("REPLACE INTO player_afk_data (uuid,playtime) VALUES('" + uuid.toString() + "','" + timePlayed + "');");
            }
        });
    }


}
