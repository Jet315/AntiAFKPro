package me.jet315.antiafkpro.manager;

import com.google.inject.Inject;
import me.jet315.antiafkpro.AntiAFKPro;
import me.jet315.antiafkpro.actions.Action;
import me.jet315.antiafkpro.actions.ActionNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores a list of Actions
 */
public class ActionController {

    @Inject
    private AntiAFKPro instance;
    private ArrayList<ActionNode> actionAFKNodes = new ArrayList<>();
    private ArrayList<ActionNode> actionPlayTimeNodes = new ArrayList<>();


    public void addActionAFKNode(ActionNode actionNode){
/*        ListIterator<ActionNode> it = actionNodes.listIterator();
        it.add(actionNode);*/
        this.actionAFKNodes.add(actionNode);
    }

    public void addActionPlaytimeNode(ActionNode actionNode){
/*        ListIterator<ActionNode> it = actionNodes.listIterator();
        it.add(actionNode);*/
        this.actionPlayTimeNodes.add(actionNode);
    }

    public ArrayList<ActionNode> getActionAFKNodes() {
        return actionAFKNodes;
    }


    public void enable(){}

    public void disable(){
        this.actionAFKNodes.clear();
        this.actionPlayTimeNodes.clear();
    }

    /**
     *
     * @param afkPlayer will check this player against actionnodes, and execute them if needed
     */
    public void checkPlayerAgainstNodes(AFKPlayer afkPlayer){
        for(Iterator<ActionNode> an = actionAFKNodes.iterator(); an.hasNext();){
            ActionNode actionNode = an.next();
            if(afkPlayer.getSecondsAFK() == actionNode.getSecondsToExecute()){
                //Called from Async, execute needs to be sync
                Bukkit.getScheduler().runTask(instance, new Runnable() {
                    @Override
                    public void run() {
                        actionNode.execute(afkPlayer.getPlayer());
                    }
                });

            }
        }

        for(Iterator<ActionNode> an = actionPlayTimeNodes.iterator(); an.hasNext();){
            ActionNode actionNode = an.next();
            if(afkPlayer.getSecondsPlayed() == actionNode.getSecondsToExecute()){
                //Called from Async, execute needs to be sync
                Bukkit.getScheduler().runTask(instance, new Runnable() {
                    @Override
                    public void run() {
                        actionNode.execute(afkPlayer.getPlayer());
                    }
                });

            }
        }

    }
}
