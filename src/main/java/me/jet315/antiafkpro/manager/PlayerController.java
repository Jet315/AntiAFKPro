package me.jet315.antiafkpro.manager;

import com.google.inject.Inject;
import me.jet315.antiafkpro.properties.Properties;
import me.jet315.antiafkpro.utils.MouseLocation;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerController {

    private HashMap<UUID,AFKPlayer> onlinePlayers = new HashMap<UUID, AFKPlayer>();

    @Inject private Properties properties;

    public PlayerController(){

    }

    public void addAFKPlayer(AFKPlayer p){
        onlinePlayers.put(p.getPlayersUUID(),p);
    }

    public void removeAFKPlayer(AFKPlayer p){
        onlinePlayers.remove(p.getPlayersUUID());
    }

    /**
     *
     * @param p The player
     * @return an AFK Player object, null if non existant
     */
    public AFKPlayer getAFKPlayer(Player p){
        return onlinePlayers.get(p.getUniqueId());
    }

    /**
     *
     * @return Returns everyone who is currently online
     */
    public HashMap<UUID, AFKPlayer> getOnlinePlayers() {
        return onlinePlayers;
    }

}
