package me.jet315.antiafkpro.properties;

import com.google.inject.Inject;
import me.jet315.antiafkpro.AntiAFKPro;
import me.jet315.antiafkpro.actions.*;
import me.jet315.antiafkpro.database.DatabaseType;
import me.jet315.antiafkpro.manager.ActionController;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Properties extends DataFile {

    @Inject
    private ActionController actionController;

    /**
     * -- Plugin Properties --
     */
    private String pluginPrefix;
    private int checkDelay;
    private DatabaseType databaseType;
    private boolean useInventory;
    /**
     * -- Database settings --
     */
    private boolean storePlayerTime;
    private String sqliteDB;

    private String mySQLHost;
    private String mySQLUser;
    private String mySQLPassword;
    private String mySQLPort;
    private String mySQLDB;
    private boolean mySQLSSL;



    @Inject
    public Properties(String configName, AntiAFKPro instance) {
        super(configName, instance);
        //load properties
    }

    /**
     * Loads the config.yml
     */
    public void enable() {
        loadProperties();
    }

    private void loadProperties() {
        ///Get values from config
        this.pluginPrefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("plugins_prefix"));
        this.checkDelay = getConfig().getInt("check_delay");
        this.useInventory = getConfig().getBoolean("enable_player_time_gui");

        //loop through actions in config
        for (String actionList : super.getConfig().getConfigurationSection("actions").getKeys(false)) {
            for (String action : super.getConfig().getConfigurationSection("actions." + actionList).getKeys(false)) {
                List<String> actions = super.getConfig().getStringList("actions." + actionList + "." + action);
                //check existence
                if (actions != null && actions.size() > 0) {
                    ArrayList<Action> actionArrayList = new ArrayList<>();

                    //loop through each individual action
                    for (String actionLine : actions) {
                        String[] actionLineSplit = actionLine.split("\\|");
                        //first part of the line contains the type
                        ActionType actionType = ActionType.valueOf(actionLineSplit[0]);
                        if (actionType != null) {
                            StringBuilder builder = new StringBuilder();
                            for (int i = 1; i < actionLineSplit.length; i++) {
                                builder.append(ChatColor.translateAlternateColorCodes('&', actionLineSplit[i]));
                            }
                            //figure out the action, add it
                            actionArrayList.add(getAction(actionType, builder.toString()));

                        } else {
                            System.out.println("AntiAFKPro > The action " + actionLineSplit[0] + " cannot be found. (Line " + actionLine + ")");
                        }

                    }

                    int seconds = Integer.valueOf(action.replaceAll("[^\\d.]", ""));
                    if (seconds <= 0) {
                        System.out.println("AntiAFKPro > The action " + action + " has an invalid timer");
                        continue;
                    }
                    if(actionList.equalsIgnoreCase("afk")){
                        actionController.addActionAFKNode(new ActionNode(seconds, actionArrayList));
                    }else{
                        actionController.addActionPlaytimeNode(new ActionNode(seconds, actionArrayList));
                    }
                }

            }
        }
        if (getConfig().getBoolean("database.mysql.use")) {
            //use myql
            databaseType = DatabaseType.MYSQL;
        } else {
            //use sqllite
            databaseType = DatabaseType.SQLITE;
        }

        //database stuff
        sqliteDB = getConfig().getString("database.sqlite.db");
        mySQLHost = getConfig().getString("database.mysql.host");
        mySQLUser = getConfig().getString("database.mysql.user");
        mySQLPassword = getConfig().getString("database.mysql.password");
        mySQLPort = getConfig().getString("database.mysql.port");
        mySQLDB = getConfig().getString("database.mysql.db");
        mySQLSSL = getConfig().getBoolean("database.mysql.ssl");
        storePlayerTime = getConfig().getBoolean("store_player_time");


    }

    private Action getAction(ActionType type, String arguments) {
        switch (type) {
            case MESSAGE:
                return new MessageAction(type, arguments);
            case COMMAND:
                return new CommandAction(type, arguments);
            case PERMISSION:
                return new PermissionAction(type, arguments);
            case SOUND:
                return new SoundAction(type, arguments);
            case TITLE:
                return new TitleAction(type, arguments);

            default:
                return new MessageAction(type, arguments);
        }
    }

    public String getPluginPrefix() {
        return pluginPrefix;
    }

    public int getCheckDelay() {
        return checkDelay;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public String getSqliteDB() {
        return sqliteDB;
    }

    public String getMySQLHost() {
        return mySQLHost;
    }

    public String getMySQLUser() {
        return mySQLUser;
    }

    public String getMySQLPassword() {
        return mySQLPassword;
    }

    public String getMySQLPort() {
        return mySQLPort;
    }

    public String getMySQLDB() {
        return mySQLDB;
    }

    public boolean isMySQLSSL() {
        return mySQLSSL;
    }

    public boolean isStorePlayerTime() {
        return storePlayerTime;
    }

    public boolean isUseInventory() {
        return useInventory;
    }
}
