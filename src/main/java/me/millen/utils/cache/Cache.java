package me.millen.utils.cache;

import org.bukkit.ChatColor;

import me.millen.utils.core.Base;

public class Cache {
    private String NO_PERMISSION, ENABLED_EFFECT, DISABLED_EFFECT, ENABLED_ALL, DISABLED_ALL;
    private String DENY_MESSAGE;
    private String MUTE_COMMAND, UNMUTE_COMMAND;
    private String BLOCKGLITCH_ENTITY, BLOCKGLITCH_SIGN, BLOCKGLITCH_MINECART;
    private java.util.List<String> GIVEAWAY_WIN, GIVEAWAY_START, GIVEAWAY_STOP;

    public void setup() {
        reload();
    }

    public void reload() {
        NO_PERMISSION = color(Base.get().getConfig().getString("messages.no-permission", "&cPermission denied."));
        ENABLED_EFFECT = color(Base.get().getConfig().getString("enabled-effect", "&aEnabled &7{effect} &f{amplifier}&7."));
        DISABLED_EFFECT = color(Base.get().getConfig().getString("disabled-effect", "&cDisabled &7{effect} &f{amplifier}&7."));
        ENABLED_ALL = color(Base.get().getConfig().getString("enabled-all", "&aEnabled &7all potion effects {nextline}(&f{effects}&7)."));
        DISABLED_ALL = color(Base.get().getConfig().getString("disabled-all", "&cDisabled &7all potion effects {nextline}(&f{effects}&7)."));
        DENY_MESSAGE = color(Base.get().getConfig().getString("deny-message", "&cBlock glitching is forbidden!"));
        MUTE_COMMAND = Base.get().getConfig().getString("mute-command", "chat mute");
        UNMUTE_COMMAND = Base.get().getConfig().getString("unmute-command", "chat unmute");
        BLOCKGLITCH_ENTITY = Base.get().getConfig().getString("blockglitch-enabled.entity", "true");
        BLOCKGLITCH_SIGN = Base.get().getConfig().getString("blockglitch-enabled.sign", "true");
        BLOCKGLITCH_MINECART = Base.get().getConfig().getString("blockglitch-enabled.minecart", "true");
        GIVEAWAY_WIN = Base.get().getConfig().getStringList("giveaway-win");
        GIVEAWAY_START = Base.get().getConfig().getStringList("giveaway-start");
        GIVEAWAY_STOP = Base.get().getConfig().getStringList("giveaway-stop");
    }

    public String color(String msg) {
        return msg != null ? ChatColor.translateAlternateColorCodes('&', msg) : "";
    }

    public String NO_PERMISSION() {
        return NO_PERMISSION;
    }

    public String ENABLED_EFFECT() {
        return ENABLED_EFFECT;
    }

    public String DISABLED_EFFECT() {
        return DISABLED_EFFECT;
    }

    public String ENABLED_ALL() {
        return ENABLED_ALL;
    }

    public String DISABLED_ALL() {
        return DISABLED_ALL;
    }

    public String DENY_MESSAGE() {
        return DENY_MESSAGE;
    }

    public String MUTE_COMMAND() {
        return MUTE_COMMAND;
    }

    public String UNMUTE_COMMAND() {
        return UNMUTE_COMMAND;
    }

    public boolean BLOCKGLITCH_ENTITY() {
        return Boolean.parseBoolean(BLOCKGLITCH_ENTITY);
    }

    public boolean BLOCKGLITCH_SIGN() {
        return Boolean.parseBoolean(BLOCKGLITCH_SIGN);
    }

    public boolean BLOCKGLITCH_MINECART() {
        return Boolean.parseBoolean(BLOCKGLITCH_MINECART);
    }

    public java.util.List<String> GIVEAWAY_WIN() {
        return GIVEAWAY_WIN;
    }

    public java.util.List<String> GIVEAWAY_START() {
        return GIVEAWAY_START;
    }

    public java.util.List<String> GIVEAWAY_STOP() {
        return GIVEAWAY_STOP;
    }
}