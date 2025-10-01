package me.millen.utils.commands.reload;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.millen.utils.core.Base;

public class Reload implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        // Allow OP players to bypass permission check
        if (sender.isOp() || sender.hasPermission("cdark.reload")) {
            Base.get().reloadAll();
            sender.sendMessage(ChatColor.GREEN + "Reloaded successfully.");
        } else {
            sender.sendMessage(Base.get().getCache().NO_PERMISSION());
        }
        return false;
    }
}