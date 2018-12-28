package me.jet315.antiafkpro.commands;

import com.google.inject.Inject;
import me.jet315.antiafkpro.AntiAFKPro;
import me.jet315.antiafkpro.gui.GUIController;
import me.jet315.antiafkpro.manager.AFKPlayer;
import me.jet315.antiafkpro.manager.PlayerController;
import me.jet315.antiafkpro.properties.Messages;
import me.jet315.antiafkpro.properties.Properties;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler implements org.bukkit.command.CommandExecutor {

    @Inject
    private AntiAFKPro plugin;
    @Inject
    private Properties properties;
    @Inject
    private Messages messages;
    @Inject
    private GUIController guiController;
    @Inject
    private PlayerController playerController;

    private Map<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>();

    @Inject
    public CommandHandler(AntiAFKPro plugin) {
        this.plugin = plugin;
        commands.put("reload", new ReloadCommand(plugin));
    }




    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        //Check for just the command /afk

        if (args.length == 0) {
            if (sender instanceof Player) {
                //if player has permission
                if (!sender.hasPermission("antiafkpro.menu")) {
                    //no perms
                    sender.sendMessage(messages.getNoPermission());
                    return false;
                }
                if(properties.isUseInventory()) {
                    Inventory inv = guiController.getTimeInventory((Player) sender);
                    if (inv != null) {
                        ((Player) sender).openInventory(inv);
                    } else {
                        //failed to load inventory
                        sender.sendMessage(messages.getFailedToLoadStats());
                    }
                }else{
                    //send player chat message of time
                    AFKPlayer afkPlayer = playerController.getAFKPlayer((Player) sender);
                    if(afkPlayer != null){
                        int playtime[] = afkPlayer.getPlaytime(true);
                        String days = String.valueOf(playtime[0]);
                        String hours = String.valueOf(playtime[1]);
                        String mins = String.valueOf(playtime[2]);
                        String secs = String.valueOf(playtime[3]);
                        String unformattedSecs = String.valueOf(afkPlayer.getSecondsPlayed());
                        sender.sendMessage(messages.getPlaytimeMessage().replaceAll("%TIME_PLAYED_DAYS%",days).replaceAll("%TIME_PLAYED_HOURS%",hours).replaceAll("%TIME_PLAYED_MINUTES%",mins).replaceAll("%TIME_PLAYED_SECONDS%",secs).replaceAll("%TIME_PLAYED_SECONDS_UNFORMATTED%",unformattedSecs));
                    }else{
                        //failed to load inventory
                        sender.sendMessage(messages.getFailedToLoadStats());
                    }

                }
            } else {
                sender.sendMessage(properties.getPluginPrefix() + ChatColor.RED + "This command is for players only. Check other players time using /afk <player>");
            }
            return false;
        }

        String name = args[0].toLowerCase();

        if (commands.containsKey(name)) {
            final CommandExecutor command = commands.get(name);

            if (command.getPermission() != null && !sender.hasPermission(command.getPermission())) {
                //no permission

            }

            if (!command.isBoth()) {
                if (command.isConsole() && sender instanceof Player) {
                    sender.sendMessage(ChatColor.RED + "Only console can use that command!");
                    return true;
                }
                if (command.isPlayer() && sender instanceof ConsoleCommandSender) {
                    sender.sendMessage(ChatColor.RED + "Only players can use that command!");
                    return true;
                }
            }

            if (command.getLength() > args.length) {
                //wrong args
                sender.sendMessage(command.getUsage());
                return true;
            }

            command.execute(sender, args);
            return true;

        }

        //command doesn't exist

        return false;
    }


    public Map<String, CommandExecutor> getCommands() {
        return commands;
    }

}