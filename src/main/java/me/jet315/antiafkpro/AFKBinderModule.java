package me.jet315.antiafkpro;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import me.jet315.antiafkpro.database.DataController;
import me.jet315.antiafkpro.gui.GUIController;
import me.jet315.antiafkpro.manager.ActionController;
import me.jet315.antiafkpro.manager.PlayerController;
import me.jet315.antiafkpro.properties.GUIProperties;
import me.jet315.antiafkpro.properties.Messages;
import me.jet315.antiafkpro.properties.Properties;
import me.jet315.antiafkpro.tasks.AFKAsyncTask;

public class AFKBinderModule extends AbstractModule {

    private final AntiAFKPro plugin;

    // This is also dependency injection, but without any libraries/frameworks since we can't use those here yet.
    public AFKBinderModule(AntiAFKPro plugin) {
        this.plugin = plugin;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    protected void configure() {
        // Here we tell Guice to use our plugin instance everytime we need it
        this.bind(AntiAFKPro.class).toInstance(this.plugin);
        this.bind(AFKAsyncTask.class).toInstance(new AFKAsyncTask());
        this.bind(PlayerController.class).toInstance(new PlayerController());
        this.bind(ActionController.class).toInstance(new ActionController());
        this.bind(DataController.class).toInstance(new DataController());
        this.bind(GUIController.class).toInstance(new GUIController());
        this.bind(Properties.class).toInstance(new Properties("config.yml",this.plugin));
        this.bind(GUIProperties.class).toInstance(new GUIProperties("timeplayedgui.yml",this.plugin));
        this.bind(Messages.class).toInstance(new Messages("messages.yml",this.plugin));


    }
}

