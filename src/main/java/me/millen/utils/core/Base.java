package me.millen.utils.core;
/*
 *  created by millen on 31/05/2020
 */

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.bukkit.plugin.java.JavaPlugin;

import me.millen.utils.cache.Cache;
import me.millen.utils.commands.giveaway.Giveaway;
import me.millen.utils.commands.potions.Potions;
import me.millen.utils.commands.reload.Reload;
import me.millen.utils.commands.spawn.SpawnCommand;
import me.millen.utils.files.Data;
import me.millen.utils.files.Updater;
import me.millen.utils.management.managers.ModuleManager;
import me.millen.utils.management.managers.ScriptManager;

public class Base extends JavaPlugin {

    public static Base get() {
        return getPlugin(Base.class);
    }

    private Cache cache;
    private Data data;
    private ModuleManager moduleManager;
    private ScriptManager scriptManager;

    public void onEnable() {
        setup();
    }

    public void onDisable() {
        saveConfig();
    }

    public void setup() {
        verifyConfiguration();

        saveResource("data.yml", false);
        data = new Data();
        data.setup();

        cache = new Cache();
        cache.setup();

        moduleManager = new ModuleManager();
        moduleManager.setup();

        scriptManager = new ScriptManager();
        scriptManager.setup();

        Potions potions = new Potions();
        SpawnCommand spawnCommand = new SpawnCommand();
        
        getCommand("giveaway").setExecutor(new Giveaway());
        getCommand("all").setExecutor(potions);
        getCommand("fr").setExecutor(potions);
        getCommand("sp").setExecutor(potions);
        getCommand("nv").setExecutor(potions);
        getCommand("invis").setExecutor(potions);
        getCommand("reload").setExecutor(new Reload());
        getCommand("spawn").setExecutor(spawnCommand);
        getCommand("setspawn").setExecutor(spawnCommand);

        getServer().getPluginManager().registerEvents(potions, this);
        getServer().getPluginManager().registerEvents(scriptManager, this);
    }

    public Cache getCache() {
        return cache;
    }

    public Data getData() { 
        return data; 
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public ScriptManager getScriptManager() {
        return scriptManager;
    }

    public void verifyConfiguration() {
        File file = new File(getDataFolder(), "config.yml");

        Updater updater = new Updater();
        try {
            if (!file.exists()) {
                saveDefaultConfig();
                reloadConfig();
                return;
            }
            updater.update(this, "config.yml", file, Collections.emptyList());
        } catch(IOException ex) {
            ex.printStackTrace();
        }

        reloadConfig();
    }

    public void reloadAll() {
        reloadConfig();
        cache.reload();
        data.reload();
        scriptManager.reload();
    }
}