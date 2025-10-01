package me.millen.utils.module.modules.giveaways;
/*
 *  created by millen on 02/06/2020
 */

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.millen.utils.core.Base;
import me.millen.utils.module.Module;
import me.millen.utils.module.Toggleable;
import me.millen.utils.utils.Formatter;

public class Giveaways extends Module implements Toggleable{

	private int number;
	private boolean state, running;

	public Giveaways(){
		super("Giveaways");
	}

	public void win(Player player){
		Bukkit.getScheduler().runTaskLater(Base.get(), () -> {
			for(String string : Base.get().getCache().GIVEAWAY_WIN())
				Base.get().getServer().broadcastMessage(Formatter.color(string).replace("{player}", player.getName()).replace("{number}", String.valueOf(getNumber())));

			setRunning(false);
			setNumber(-1);
		}, 1);
	}

	public void start(Player player, int number){
		setNumber(new Random().nextInt(number));

		Base.get().getServer().dispatchCommand(player, Base.get().getCache().MUTE_COMMAND());
		Base.get().getCache().GIVEAWAY_START().forEach(line -> Base.get().getServer().broadcastMessage(Formatter.color(line).replace("{number}", String.valueOf(number)).replace("{player}", player.getName())));
		setRunning(true);

		Bukkit.getScheduler().runTaskLater(Base.get(), () -> {
			if(isRunning())
				Base.get().getServer().dispatchCommand(player, Base.get().getCache().UNMUTE_COMMAND());
		}, 20 * 5);
	}

	public void stop(Player player){
		Base.get().getCache().GIVEAWAY_STOP().forEach(line -> Base.get().getServer().broadcastMessage(Formatter.color(line).replace("{player}", player.getName())));

		setNumber(-1);
		setRunning(false);
	}

	public boolean isRunning(){
		return running;
	}

	private void setRunning(boolean running){
		this.running = running;
	}

	public int getNumber(){
		return number;
	}

	private void setNumber(int number){
		this.number = number;
	}

	@Override
	public boolean getState(){
		return state;
	}

	@Override
	public void setState(boolean state){
		this.state = state;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void chat(AsyncPlayerChatEvent event){
		if(!isRunning()) return;

		if(event.getMessage().contains(String.valueOf(number)))
			win(event.getPlayer());
	}
}
