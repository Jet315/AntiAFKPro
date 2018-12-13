package me.jet315.antiafkpro.actions;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundAction extends Action {
    private Sound sound;

    public SoundAction(ActionType type, String actionArgument) {
        super(type, actionArgument);
        try {
            sound = Sound.valueOf(actionArgument);
        }catch (IllegalArgumentException e){
            System.out.println("AntiAFKPro > INVALID SOUND SETTING: '" + actionArgument + "' Not found");
        }
    }

    @Override
    public boolean execute(Player p) {
        if (sound != null) {
            p.playSound(p.getLocation(), sound, (float) 1, (float) 1);
            return true;
        }
        System.out.println("AntiAFKPro > INVALID SOUND SETTING: '" + getActionArgument() + "' Not found");
        return false;
    }
}
