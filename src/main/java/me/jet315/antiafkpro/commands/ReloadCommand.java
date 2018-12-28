package me.jet315.antiafkpro.commands;

import com.google.inject.Inject;
import me.jet315.antiafkpro.AntiAFKPro;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends CommandExecutor{

    @Inject
    private AntiAFKPro instance;

    /**
     * Reloads the configuration file
     */

    public ReloadCommand(AntiAFKPro instance) {
        this.instance = instance;
        setCommand("reload");
        setPermission("antiafkpro.admin.reload");
        setLength(1);
        setBoth();
        setUsage("/playtime reload");

    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        long startTime = System.currentTimeMillis();
        instance.reloadConfiguration();
        long endtime = System.currentTimeMillis() - startTime;
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',  "&aReload Complete: &6" + endtime + "ms"));


    }
}
