package me.jet315.antiafkpro.properties;

import com.google.inject.Inject;
import me.jet315.antiafkpro.AntiAFKPro;
import me.jet315.antiafkpro.manager.ActionController;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class DataFile {

    @Inject
    protected AntiAFKPro instance;

    /**
     *      -- File Fields --
     */
    private File file;
    private FileConfiguration config;
    private String configName;

    //I have to pass instance through constructor, as otherwise it'll be null as I'm calling methods from the constructor
    //(Instance class wouldn't have injected in time)
    @Inject
    public DataFile(String configName, AntiAFKPro instance){
        this.instance = instance;
        this.configName = configName;
        createFile(configName);
        loadYml(configName);
    }


    /**
     *
     * Creates a configuration file
     *
     * @param configName The name of the configuration file to create
     */
    private void createFile(String configName) {
        try {
            if (!instance.getDataFolder().exists()) {
                instance.getDataFolder().mkdirs();
            }
            file = new File(instance.getDataFolder(), configName);
            if (!file.exists()) {
                instance.getLogger().info(configName + " not found, creating!");
                instance.saveResource(configName,false);
            } else {
                instance.getLogger().info(configName + " found, loading!");
            }
        } catch (Exception e) {
            System.out.println(configName + " failed to load..");
            e.printStackTrace();
        }
    }

    /**
     *
     * Loads a YamlConfiguration given a file name
     *
     * @param configName The name of the YML to load
     */
    private void loadYml(String configName){
        config = YamlConfiguration.loadConfiguration(new File(instance.getDataFolder(),configName));
    }

    /**
     * Loads properties from file
     */
     public abstract void enable();

    /**
     *
     * @return The file object
     */
    public File getFile() {
        return file;
    }
    /**
     *
     * @return The configuration object
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Saves the configuration - Called if the file has been modified
     */
    public void saveFile(){
        try{
            config.save(file);
        }catch (IOException e){
            System.out.println(e);
        }
    }

    protected void reload(){
        loadYml(configName);
    }


}