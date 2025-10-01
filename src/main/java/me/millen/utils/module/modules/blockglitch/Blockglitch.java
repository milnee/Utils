package me.millen.utils.module.modules.blockglitch;
/*
 *  created by millen on 03/06/2020
 */

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import me.millen.utils.core.Base;
import me.millen.utils.module.Module;

public class Blockglitch extends Module{

	private final Map<UUID, Long> times;

	public Blockglitch(){
		super("Blockglitch");
		this.times = new HashMap<>();
	}

	public void disable() {
		this.times.clear();
	}

	public void checkAndCancel(Player player, Cancellable event) {
		if(this.times.containsKey(player.getUniqueId()) && System.currentTimeMillis() - this.times.get(player.getUniqueId()) < 500L) {
			event.setCancelled(true);

			if(!Base.get().getCache().DENY_MESSAGE().equals(""))
				player.sendMessage(Base.get().getCache().DENY_MESSAGE());
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		this.times.remove(event.getPlayer().getUniqueId());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		if(!event.isCancelled() || !event.getBlock().getType().isSolid()) return;

		this.times.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!Base.get().getCache().BLOCKGLITCH_ENTITY() || !(event.getDamager() instanceof Player)) return;

		this.checkAndCancel((Player) event.getDamager(), event);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(!Base.get().getCache().BLOCKGLITCH_SIGN() || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

		Block block = event.getClickedBlock();
		if(block == null || (block.getType() != Material.SIGN_POST && block.getType() != Material.WALL_SIGN)) return;

		this.checkAndCancel(event.getPlayer(), event);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onVehicleEnter(VehicleEnterEvent event) {
		if(!Base.get().getCache().BLOCKGLITCH_MINECART() || !(event.getEntered() instanceof Player) || !(event.getVehicle() instanceof Minecart)) return;

		this.checkAndCancel((Player) event.getEntered(), event);
	}
}
