package me.millen.utils.utils;
/*
 *  created by millen on 02/06/2020
 */

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class Formatter{

	public static String color(String text){
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public static List<String> color(List<String> list){
		List<String> newList = new ArrayList<>();
		for(String string : list)
			newList.add(ChatColor.translateAlternateColorCodes('&', string));

		return newList;
	}

	public static List<String> strip(List<String> list){
		List<String> newList = new ArrayList<>();
		for(String string : list)
			newList.add(string.replaceAll("§", "&"));

		return newList;
	}
}
