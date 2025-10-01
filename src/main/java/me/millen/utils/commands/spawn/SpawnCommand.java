package me.millen.utils.commands.spawn;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.millen.utils.core.Base;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return false;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("setspawn")) {
            // Check if player has permission or is OP
            if (!player.isOp() && !player.hasPermission("utils.setspawn")) {
                player.sendMessage(Base.get().getCache().NO_PERMISSION());
                return false;
            }

            // Save spawn location
            Location loc = player.getLocation();
            FileConfiguration config = Base.get().getConfig();
            config.set("spawn.world", loc.getWorld().getName());
            config.set("spawn.x", loc.getX());
            config.set("spawn.y", loc.getY());
            config.set("spawn.z", loc.getZ());
            config.set("spawn.yaw", loc.getYaw());
            config.set("spawn.pitch", loc.getPitch());
            Base.get().saveConfig();

            player.sendMessage(ChatColor.GREEN + "Spawn point has been set!");
            return true;
        }

        if (command.getName().equalsIgnoreCase("spawn")) {
            // Check if player has permission or is OP
            if (!player.isOp() && !player.hasPermission("utils.spawn")) {
                player.sendMessage(Base.get().getCache().NO_PERMISSION());
                return false;
            }

            // Get spawn location
            FileConfiguration config = Base.get().getConfig();
            if (!config.contains("spawn.world")) {
                player.sendMessage(ChatColor.RED + "Spawn point has not been set!");
                return false;
            }

            Location spawn = new Location(
                Base.get().getServer().getWorld(config.getString("spawn.world")),
                config.getDouble("spawn.x"),
                config.getDouble("spawn.y"),
                config.getDouble("spawn.z"),
                (float) config.getDouble("spawn.yaw"),
                (float) config.getDouble("spawn.pitch")
            );

            player.teleport(spawn);
            player.sendMessage(ChatColor.GREEN + "Teleported to spawn!");
            return true;
        }

        return false;
    }
}