package me.millen.utils.module;

import org.bukkit.event.Listener;

public class Module implements Listener{

	private String label;

	public Module(String label){
		this.label = label;
	}

	public String getLabel(){
		return label;
	}
}
