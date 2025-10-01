package me.millen.utils.commands.potions;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.Lists;

import me.millen.utils.core.Base;

public class Potions implements CommandExecutor, Listener {

    private List<UUID> all = Lists.newArrayList();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command only executable as a player.");
            return false;
        }

        Player player = (Player) sender;
        
        // Allow OP players to bypass permission check
        if (!player.isOp() && !player.hasPermission("utils.potions")) {
            player.sendMessage(Base.get().getCache().NO_PERMISSION());
            return false;
        }

        // Allow OP players to bypass specific command permission
        if (!player.isOp() && !player.hasPermission("utils.potions." + command.getName())) {
            player.sendMessage(Base.get().getCache().NO_PERMISSION());
            return false;
        }

        if (getEffect(command.getName()) != null) {
            String effect = StringUtils.capitaliseAllWords(getEffect(command.getName()).getName().toLowerCase().replace("_", " "));
            player.sendMessage((toggleEffect(player, getEffect(command.getName())) ?
                    Base.get().getCache().ENABLED_EFFECT().replace("{effect}", effect).replace("{amplifier}", "II") :
                    Base.get().getCache().DISABLED_EFFECT().replace("{effect}", effect).replace("{amplifier}", "II")));
        } else {
            PotionEffectType[] types = new PotionEffectType[]{ PotionEffectType.INVISIBILITY, PotionEffectType.SPEED, PotionEffectType.NIGHT_VISION, PotionEffectType.FIRE_RESISTANCE };
            if (hasAll(player, types)) {
                StringBuilder builder = new StringBuilder();
                for (PotionEffectType type : types) {
                    toggleEffect(player, type);
                    builder.append(StringUtils.capitaliseAllWords(type.getName().toLowerCase().replace("_", " "))).append(" II, ");
                }

                String effects = builder.toString().substring(0, builder.toString().length() - 2);

                player.sendMessage(Base.get().getCache().DISABLED_ALL().replace("{nextline}", System.lineSeparator()).replace("{effects}", effects));
            } else {
                StringBuilder builder = new StringBuilder();
                List<PotionEffectType> list = addAll(player, types);
                list.forEach(line -> builder.append(StringUtils.capitaliseAllWords(line.getName().toLowerCase().replace("_", " "))).append(" II, "));
                String effects = builder.toString().substring(0, builder.toString().length() - 2);

                player.sendMessage(Base.get().getCache().ENABLED_ALL().replace("{nextline}", System.lineSeparator()).replace("{effects}", effects));
            }
        }

        return false;
    }

    public PotionEffectType getEffect(String aliase) {
        switch(aliase) {
            case "fr":
            case "fireresistance":
                return PotionEffectType.FIRE_RESISTANCE;
            case "nv":
            case "nightvision":
                return PotionEffectType.NIGHT_VISION;
            case "sp":
                return PotionEffectType.SPEED;
            case "invis":
            case "invisibility":
                return PotionEffectType.INVISIBILITY;
        }

        return null;
    }

    public boolean toggleEffect(Player player, PotionEffectType type) {
        if (player.hasPotionEffect(type)) {
            player.removePotionEffect(type);
            return false;
        } else {
            player.addPotionEffect(new PotionEffect(type, Integer.MAX_VALUE, 1, true));
            return true;
        }
    }

    public List<PotionEffectType> addAll(Player player, PotionEffectType[] types) {
        List<PotionEffectType> list = Lists.newArrayList();
        Arrays.stream(types).filter(type -> !player.hasPotionEffect(type)).forEach(type -> {
            player.addPotionEffect(new PotionEffect(type, Integer.MAX_VALUE, 1, true));
            list.add(type);
        });

        return list;
    }

    public boolean hasAll(Player player, PotionEffectType[] types) {
        for (PotionEffectType type : types)
            if (!player.hasPotionEffect(type))
                return false;

        return true;
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        PotionEffectType[] types = new PotionEffectType[]{ PotionEffectType.INVISIBILITY, PotionEffectType.SPEED, PotionEffectType.NIGHT_VISION, PotionEffectType.FIRE_RESISTANCE };
        List<PotionEffectType> list = Arrays.asList(types);
        for (PotionEffect effect : event.getPlayer().getActivePotionEffects()) {
            // check if the effect has a duration longer than 16 minutes, then remove cause it has
            // been given by a command since max duration for vanilla is 8 minutes !
            if ((effect.getDuration() / 20) > (16 * 60) && list.stream().anyMatch(type -> effect.getType().equals(type)))
                event.getPlayer().removePotionEffect(effect.getType());
        }
    }
}