package me.jet315.antiafkpro.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MouseLocation {

    private float yaw;
    private float pitch;

    /**
     *
     * @param loc The location of the player
     */
    public MouseLocation(Location loc){
        this.yaw = loc.getYaw();
        this.pitch = loc.getPitch();
    }

    /**
     *
     * @param yaw The current yaw of the player
     * @param pitch The current pitch of the player
     */
    public MouseLocation(float yaw, float pitch){
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     *
     * @param p A Minecraft Player Object
     */
    public MouseLocation(Player p){
        this.yaw = p.getLocation().getYaw();
        this.pitch = p.getLocation().getPitch();
    }

    /**
     *
     * @return The yaw of this mouse position
     */
    public float getYaw() {
        return yaw;
    }

    /**
     *
     * @return The pitch of this mouse position
     */
    public float getPitch() {
        return pitch;
    }

    public boolean isMouseLocationEqual(MouseLocation loc){
        if(loc.pitch == pitch && loc.yaw == yaw) return true;
        return false;
    }
}
