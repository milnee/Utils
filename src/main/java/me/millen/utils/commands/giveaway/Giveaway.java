package me.millen.utils.commands.giveaway;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.millen.utils.core.Base;
import me.millen.utils.module.modules.giveaways.Giveaways;
import me.millen.utils.utils.Formatter;

public class Giveaway implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command can only be used as a player.");
            return false;
        }

        Player player = (Player) sender;
        
        // Allow OP players to bypass permission check
        if (!player.isOp() && !player.hasPermission("utils.giveaway")) {
            player.sendMessage(Base.get().getCache().NO_PERMISSION());
            return false;
        }

        if (args.length < 1) {
            sendUsage(sender);
        } else {
            Giveaways giveaways = (Giveaways) Base.get().getModuleManager().get(Giveaways.class);
            if (args[0].equalsIgnoreCase("start")) {
                if (args.length != 2) {
                    sender.sendMessage(Formatter.color("&7Usage: /giveaway start (max)."));
                } else {
                    if (!isInt(args[1])) {
                        sender.sendMessage(Formatter.color("&cYou must specify the max with numbers."));
                    } else {
                        if (giveaways.isRunning()) {
                            sender.sendMessage(Formatter.color("&cThere is a giveaway running already."));
                        } else {
                            giveaways.start(player, Integer.parseInt(args[1]));
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("stop")) {
                if (args.length != 1) {
                    sender.sendMessage(Formatter.color("&7Usage: /giveaway stop."));
                } else {
                    if (!giveaways.isRunning()) {
                        sender.sendMessage(Formatter.color("&7There isn't a giveaway running."));
                    } else {
                        giveaways.stop(player);
                    }
                }
            } else {
                sendUsage(sender);
            }
        }

        return false;
    }

    public boolean isInt(String parse) {
        try {
            Integer.parseInt(parse);
        } catch(NumberFormatException ex) {
            return false;
        }

        return true;
    }

    public void sendUsage(CommandSender sender) {
        sender.sendMessage(Formatter.color("&7&m----------------------------------------"));
        sender.sendMessage(Formatter.color("&7/giveaway start (max) - Start a giveaway"));
        sender.sendMessage(Formatter.color("&7/giveaway stop - Stop giveaway"));
        sender.sendMessage(Formatter.color("&7&m----------------------------------------"));
    }
}