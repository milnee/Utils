package me.millen.utils.script;
/*
 *  created by millen on 05/06/2020
 */

import java.util.List;

public class Script{

	private String label, permission;
	private List<String> messages;

	public Script(String label, String permission, List<String> messages){
		this.label = label;
		this.permission = permission;
		this.messages = messages;
	}

	public String getLabel(){
		return label;
	}

	public String getPermission(){ return permission; }

	public List<String> getMessages(){
		return messages;
	}
}