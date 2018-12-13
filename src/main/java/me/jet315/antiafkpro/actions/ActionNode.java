package me.jet315.antiafkpro.actions;

import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * The main point for an ActionNode is it contains many actions
 */
public class ActionNode {

    private int secondsToExecute;
    private ArrayList<Action> actionsToExecute = new ArrayList<>();
    private ArrayList<Action> permissionsToExecute = new ArrayList<>();



    public ActionNode(int secondsToExecute, ArrayList<Action> actionsToExecute) {
        this.secondsToExecute = secondsToExecute;

        for(Action action : actionsToExecute){
            if(action.getActionType() == ActionType.PERMISSION){
                permissionsToExecute.add(action);
            }
            this.actionsToExecute.add(action);
        }
    }


    /**
     *
     * @return The seconds needed to execute this node
     */
    public int getSecondsToExecute() {
        return secondsToExecute;
    }

    /**
     *
     * @param p The player object
     * @return True if execution was successful, false otherwise
     */
    public boolean execute(Player p){
        if(p != null){
            if(playerHasPermission(p)) {
                for (Action action : actionsToExecute) {
                    action.execute(p);
                }
                return true;
            }
        }
        return false;
    }

    private boolean playerHasPermission(Player p){
        for(Action action : permissionsToExecute){
            if(!action.execute(p)){
                return false;
            }
        }
        return true;
    }

    public ArrayList<Action> getActionsToExecute() {
        return actionsToExecute;
    }
    public ArrayList<Action> getPermissionsToExecute() {
        return permissionsToExecute;
    }
}
