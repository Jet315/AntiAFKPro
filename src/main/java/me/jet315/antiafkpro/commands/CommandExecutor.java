package me.jet315.antiafkpro.commands;

import org.bukkit.command.CommandSender;

public abstract class CommandExecutor {

    //Fields
    private String command;
    private String permission;
    private String usage;
    private boolean console;
    private boolean player;
    private int length;

    /**
     *
     * @param sender
     * @param args
     */
    public abstract void execute(CommandSender sender, String[] args);

    /**
     * Sets it so the command may be used by a console & player
     */
    public void setBoth(){
        this.player = true;
        this.console = true;
    }

    /**
     *
     * @return Whether the command may be exeucted by the console & player
     */
    public boolean isBoth(){
        return player && console;
    }

    /**
     *
     * @return The command
     */
    public String getCommand() {
        return command;
    }

    /**
     *
     * @param command The command
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     *
     * @return The permission needed to execute the command
     */
    public String getPermission() {
        return permission;
    }

    /**
     *
     * @param permission The permission needed to execute the command
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     *
     * @return Whether the command may be executed by the console
     */
    public boolean isConsole() {
        return console;
    }
    /**
     *
     * @return Whether the command may be executed by the player
     */
    public boolean isPlayer() {
        return player;
    }

    /**
     * Sets console as required sender
     */
    public void setConsole() {
        this.console = true;
        this.player = false;
    }


    /**
     * Sets Player as required sender
     */
    public void setPlayer() {
        this.player = true;
        this.console = false;
    }

    /**
     *
     * @return Length of the command
     */
    public int getLength() {
        return length;
    }

    /**
     *
     * @param length sets the length of the command
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     *
     * @return The usage of the command
     */
    public String getUsage() {
        return usage;
    }

    /**
     *
     * @param usage The usage of the command
     */
    public void setUsage(String usage) {
        this.usage = usage;
    }
}
