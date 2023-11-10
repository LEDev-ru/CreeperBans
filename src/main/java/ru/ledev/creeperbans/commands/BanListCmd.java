package ru.ledev.creeperbans.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.ledev.creeperbans.managers.DBManager;

import java.util.List;

public class BanListCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, @NotNull String[] args) {

        List<DBManager.BannedPlayer> banList = DBManager.INSTANCE.getBanList();

        if (banList.isEmpty()) {
            sender.sendMessage("Banlist is empty!");
            return true;
        }

        String[] message = new String[banList.size() + 1];
        message[0] = "Banlist:";
        for (int i = 1; i < message.length; i++) message[i] = i + ". " + banList.get(i - 1).name + " - " + banList.get(i - 1).reason;
        sender.sendMessage(message);

        return true;
    }
}
