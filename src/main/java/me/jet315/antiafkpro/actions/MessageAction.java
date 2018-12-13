package me.jet315.antiafkpro.actions;

import org.bukkit.entity.Player;

public class MessageAction extends Action{


    public MessageAction(ActionType type, String actionArgument) {
        super(type, actionArgument);
    }

    @Override
    public boolean execute(Player p) {
        p.sendMessage(getActionArgument().replaceAll("%PLAYER%",p.getName()));
        return true;

    }
}
