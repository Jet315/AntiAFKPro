package me.jet315.antiafkpro.actions;

import org.bukkit.entity.Player;

public class PermissionAction extends Action{

    public PermissionAction(ActionType type, String actionArgument) {
        super(type, actionArgument);
    }

    @Override
    public boolean execute(Player p) {
        if(p.hasPermission(getActionArgument())){
            return true;
        }
        return false;
    }
}
