package me.jet315.antiafkpro.database;

import com.zaxxer.hikari.HikariDataSource;
import me.jet315.antiafkpro.AntiAFKPro;
import me.jet315.antiafkpro.properties.Properties;

import java.sql.*;

public class MySQL extends Database{

    private final String user;
    private final String database;
    private final String password;
    private final String port;
    private final String hostname;

    private HikariDataSource hikari;
    private Connection connection;

    public MySQL(AntiAFKPro instance, Properties properties, String hostname, String port, String database, String username, String password) {
        super(DatabaseType.MYSQL,instance,properties);
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = username;
        this.password = password;
        this.connection = null;
    }

    public Connection openConnection() throws SQLException {
        connection = hikari.getConnection();
        return connection;
    }

    public boolean isConnectionOpen() throws SQLException {
        if(connection != null && !connection.isClosed()){
            return true;
        }
        return false;
    }

    public Connection getConnection() throws SQLException {
        if(!isConnectionOpen()){
            return openConnection();
        }
        return connection;
    }

    public boolean closeConnection() {
        try {
            if (isConnectionOpen()) {
                connection.close();
            }
        }catch (SQLException e){
            return false;
        }

        return true;
    }


    public boolean initialise() {

        //setup Hikari
        hikari = new HikariDataSource();
        hikari.setJdbcUrl("jdbc:mysql://"+hostname+":"+port+"/"+database);

        hikari.addDataSourceProperty("user", user);
        hikari.addDataSourceProperty("password", password);
        hikari.addDataSourceProperty("useSSL",properties.isMySQLSSL() ? "yes" : "no");


        //setup table
        try(Connection con = hikari.getConnection();
            Statement statement = con.createStatement()){
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS player_afk_data(uuid CHAR(36), playtime int)");

            //Alter table if needed (I need to make the first row UNIQUE for the plugin to work, so I do this way)
            ResultSet result = statement.executeQuery("SELECT uuid FROM player_afk_data order by uuid asc limit 1;");
            if(!result.next()){
                statement.executeUpdate("ALTER TABLE player_afk_data ADD CONSTRAINT uuid UNIQUE(uuid)");
            }
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int queryPlayTime(String query) {
        try {
            //So as MySQL can be used cross server, its important to wait if they switch from one server to another, previous data needs to have saved
            Thread.sleep(400);
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

        } catch (SQLException | InterruptedException e) {
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
