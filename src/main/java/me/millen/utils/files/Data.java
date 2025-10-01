package me.millen.utils.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.millen.utils.core.Base;

public class Data{

    private File file = new File(Base.get().getDataFolder(), "data.yml");
    private FileConfiguration data = YamlConfiguration.loadConfiguration(file);

    public void setup(){
        if(!file.exists())
            saveOnly();
    }

    public void saveOnly(){
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload(){
        data = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getFile() {
        return data;
    }
}
