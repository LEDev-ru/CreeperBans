package ru.ledev.creeperbans.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;
import org.jetbrains.annotations.NotNull;
import ru.ledev.creeperbans.managers.DBManager;

public class BanCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, @NotNull String[] args) {

        if (args.length < 1) return true;

        String playerName = args[0].toLowerCase();

        String reason;
        if (args.length < 2) {
            reason = "Banned by administrator";
        }
        else {
            StringBuilder sb = new StringBuilder(args[1]);
            for (int i = 2; i < args.length; i++) {
                sb.append(" ").append(args[i]);
            }
            reason = sb.toString();
        }

        if (!DBManager.INSTANCE.ban(playerName, reason)) {
            sender.sendMessage(args[0] + "is already banned!");
            return true;
        }

        Player bannedPlayer = Bukkit.getPlayer(playerName);
        if (bannedPlayer != null) {
            bannedPlayer.kick(Component.text("You are banned on this server! Reason: " + reason).color(TextColor.color(235, 31, 16)), PlayerKickEvent.Cause.BANNED);
        }

        sender.sendMessage(args[0] + " banned (Reason: " + reason + ")");

        return true;
    }
}
