package ru.ledev.creeperbans.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import ru.ledev.creeperbans.managers.DBManager;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent e) {

        String name = e.getName().toLowerCase();

        if (DBManager.INSTANCE.isBanned(name)) {
            e.disallow(Result.KICK_BANNED, Component.text("You are banned on this server! Reason: " + DBManager.INSTANCE.getBanReason(name)).color(TextColor.color(235, 31, 16)));
        }
    }
}
