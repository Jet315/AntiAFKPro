package me.jet315.antiafkpro.actions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandAction extends Action{


    public CommandAction(ActionType type, String actionArgument) {
        super(type, actionArgument);
    }

    @Override
    public boolean execute(Player p) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),getActionArgument().replaceAll("%PLAYER%",p.getName()));
        return true;
    }
}
