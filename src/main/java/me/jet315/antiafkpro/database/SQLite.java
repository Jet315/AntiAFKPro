package me.jet315.antiafkpro.database;

import me.jet315.antiafkpro.AntiAFKPro;
import me.jet315.antiafkpro.manager.AFKPlayer;
import me.jet315.antiafkpro.properties.Properties;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;

public class SQLite extends Database {

    private final String sqliteDB;
    private File databaseFile;

    private Connection connection;

    public String sqliteCreateTable = "CREATE TABLE IF NOT EXISTS player_afk_data (" +
            "`uuid` varchar(32) NOT NULL," + // UUID
            "`playtime` int(32) NOT NULL," + //Stored as milliseconds since epoch of time expire
            "PRIMARY KEY (`uuid`)" +
            ");";

    public SQLite(AntiAFKPro instance, Properties properties, String sqliteDB) {
        super(DatabaseType.SQLITE, instance, properties);
        this.sqliteDB = sqliteDB;
    }


    public Connection openConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile);
        return connection;
    }

    public boolean isConnectionOpen() throws SQLException {
        if (connection == null || connection.isClosed()) {
            return false;
        }
        return true;
    }

    public Connection getConnection() throws SQLException {
        if (!isConnectionOpen()) {
            return openConnection();
        }
        return connection;
    }

    public boolean closeConnection() throws SQLException {
        connection.close();
        return false;
    }

    public boolean initialise() {
        databaseFile = new File(super.instance.getDataFolder(), sqliteDB + ".db");
        if (!databaseFile.exists()) {
            try {
                databaseFile.createNewFile();
            } catch (IOException e) {
                super.instance.getLogger().log(Level.SEVERE, "File write error: " + sqliteDB + ".db");
                return false;
            }
        }
        try {
            Statement s = openConnection().createStatement();
            s.executeUpdate(sqliteCreateTable);
            s.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    public int queryPlayTime(String query) {
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int timePlayed = 0;
            if(rs != null){
                try {
                    if (rs.next()) {
                        timePlayed = rs.getInt("playtime");
                    }
                }catch (SQLException e){
                    System.out.println(e);
                    System.out.println("AntiAFKPro > FAILED TO QUERY DATABASE");
                }
            }

            rs.close();
            ps.close();
            closeConnection();
            return timePlayed;

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("AntiAFKPro > FAILED TO QUERY DATABASE");
            return 0;
        }
    }
    public void queryVoid(String query) {
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.executeUpdate();
            ps.close();
            closeConnection();

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("AntiAFKPro > FAILED TO QUERY DATABASE");
        }
    }

}
