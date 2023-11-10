package ru.ledev.creeperbans.managers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    public static final DBManager INSTANCE = new DBManager();

    private final Connection connection;
    private final Statement statement;

    private DBManager() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/CreeperBans/db.sqlite");
            statement = connection.createStatement();

            statement.execute(
                    "CREATE TABLE IF NOT EXISTS BanList(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "reason TEXT);"
            );
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isBanned(String playerName) {
        for (BannedPlayer p : getBanList()) {
            if (p.name.equalsIgnoreCase(playerName)) return true;
        }
        return false;
    }

    public String getBanReason(String playerName) {
        for (BannedPlayer p : getBanList()) {
            if (p.name.equalsIgnoreCase(playerName)) return p.reason;
        }
        return "null";
    }

    public boolean ban(String playerName, String reason) {

        if (isBanned(playerName)) return false;

        try {

            String sql = "INSERT INTO BanList(name, reason) VALUES(?, ?);";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, playerName);
            ps.setString(2, reason);

            ps.executeUpdate();
            ps.close();

            return true;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean unban(String playerName) {
        if (!isBanned(playerName)) return false;

        try {

            int id = -1;

            for (BannedPlayer p : getBanList()) {
                if (p.name.equalsIgnoreCase(playerName)) {
                    id = p.id;
                    break;
                }
            }

            if (id == -1) return false;

            statement.execute("DELETE FROM BanList WHERE id = " + id + ";");

            return true;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public @NotNull List<BannedPlayer> getBanList() {

        try {

            List<BannedPlayer> banList = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM BanList;");

            while (resultSet.next()) {
                banList.add(new BannedPlayer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }

            return banList;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static class BannedPlayer {
        public final int id;
        public final String name;
        public final String reason;

        public BannedPlayer(int id, String name, String reason) {
            this.id = id;
            this.name = name;
            this.reason = reason;
        }
    }
}
