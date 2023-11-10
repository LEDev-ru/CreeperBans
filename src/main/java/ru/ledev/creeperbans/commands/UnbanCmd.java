package ru.ledev.creeperbans.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.ledev.creeperbans.managers.DBManager;

import java.util.ArrayList;
import java.util.List;

public class UnbanCmd implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, @NotNull String[] args) {

        if (args.length < 1) return true;

        String playerName = args[0].toLowerCase();

        if (!DBManager.INSTANCE.unban(playerName)) {
            sender.sendMessage(args[0] + " isn't banned!");
            return true;
        }

        sender.sendMessage(args[0] + " unbanned!");

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, @NotNull String[] args) {

        if (args.length == 1) {
            List<String> bannedPlayers = new ArrayList<>();
            for (DBManager.BannedPlayer p : DBManager.INSTANCE.getBanList()) bannedPlayers.add(p.name);
            if (args[0].isEmpty()) return bannedPlayers;
            List<String> result = new ArrayList<>();
            for (String n : bannedPlayers) {
                if (n.toLowerCase().contains(args[0].toLowerCase())) result.add(n);
            }
            return result;
        }

        return new ArrayList<>();
    }
}
