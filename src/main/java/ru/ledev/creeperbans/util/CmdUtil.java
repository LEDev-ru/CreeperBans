package ru.ledev.creeperbans.util;

import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CmdUtil {

    private static final TabCompleter EMPTY_TAB_COMPLETER = new TabCompleter() {
        @Override
        public @NotNull List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
            return new ArrayList<>();
        }
    };

    private final JavaPlugin plugin;

    public CmdUtil(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean reg(String commandName, TabExecutor tabExecutor) {

        PluginCommand cmd = plugin.getCommand(commandName);

        if (cmd == null) return false;

        cmd.setExecutor(tabExecutor);
        cmd.setTabCompleter(tabExecutor);

        return true;
    }

    public boolean reg(String commandName, CommandExecutor commandExecutor, boolean disableTabComplete) {

        PluginCommand cmd = plugin.getCommand(commandName);

        if (cmd == null) return false;

        cmd.setExecutor(commandExecutor);

        if (disableTabComplete) {
            cmd.setTabCompleter(EMPTY_TAB_COMPLETER);
        }

        return true;
    }

}
