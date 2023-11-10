package ru.ledev.creeperbans;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import ru.ledev.creeperbans.commands.*;
import ru.ledev.creeperbans.events.PlayerListener;
import ru.ledev.creeperbans.util.CmdUtil;

public final class CreeperBans extends JavaPlugin {

    private static CreeperBans instance;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        CmdUtil cu = new CmdUtil(this);

        if (!cu.reg("cban", new BanCmd(), false)
                || !cu.reg("cunban", new UnbanCmd())
                || !cu.reg("cbanlist", new BanListCmd(), true)
        ) {
            getLogger().warning("Error registering commands! Goodbye!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

    }

    @Override
    public void onDisable() {

        HandlerList.unregisterAll(this);

        getLogger().info("Goodbye!");

    }

    public static CreeperBans getInstance() {
        return instance;
    }
}
