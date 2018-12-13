package me.jet315.antiafkpro.actions;

import org.bukkit.entity.Player;

public abstract class Action {

    private ActionType actionType;
    private String actionArgument;

    public Action(ActionType type, String actionArgument){
        this.actionType = type;
        this.actionArgument = actionArgument;
    }

    /**
     *
     * @param p The player object
     */
    public abstract boolean execute(Player p);



    /**
     *
     * @return The action type
     */
    public ActionType getActionType() {
        return actionType;
    }

    /**
     *
     * @return The argument for the action
     */
    protected String getActionArgument() {
        return actionArgument;
    }
}
