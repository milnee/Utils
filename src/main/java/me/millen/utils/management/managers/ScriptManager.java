package me.millen.utils.management.managers;
/*
 *  created by millen on 05/06/2020
 */

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.millen.utils.core.Base;
import me.millen.utils.management.Manager;
import me.millen.utils.script.Script;
import me.millen.utils.utils.Formatter;

public class ScriptManager extends Manager implements Listener{

	private List<Script> scripts = new ArrayList<>();

	public void setup(){
		for(String key : Base.get().getData().getFile().getConfigurationSection("scripts").getKeys(false))
			scripts.add(new Script(key, Base.get().getData().getFile().getString("scripts." +key +".permission"), Base.get().getData().getFile().getStringList("scripts." + key + ".messages")));
	}

	public List<Script> getScripts(){
		return scripts;
	}

	public void reload(){
		scripts.clear();
		setup();
	}

	@EventHandler
	public void preCommand(PlayerCommandPreprocessEvent event){
		if(event.getMessage().startsWith("/")){
			String command = event.getMessage().replaceFirst("/", "");
			for(Script script : getScripts()){
				if(script.getLabel().equalsIgnoreCase(command) && event.getPlayer().hasPermission(script.getPermission())){
					event.setCancelled(true);
					Formatter.color(script.getMessages()).forEach(line -> event.getPlayer().sendMessage(line));
				}
			}
		}
	}
}
