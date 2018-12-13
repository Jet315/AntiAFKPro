package me.jet315.antiafkpro.database;

import com.google.inject.Inject;
import me.jet315.antiafkpro.AntiAFKPro;
import me.jet315.antiafkpro.properties.Properties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Database {

    private DatabaseType databaseType;

    protected AntiAFKPro instance;
    protected Properties properties;

    public Database(DatabaseType type, AntiAFKPro instance, Properties properties) {
        this.instance = instance;
        this.properties = properties;
        this.databaseType = type;
    }

    /**
     * Opens a connection with the database.
     *
     * @return Opened connection
     *
     * @throws SQLException if the connection can not be opened
     * @throws ClassNotFoundException if the driver cannot be found
     */
    public abstract Connection openConnection() throws SQLException, ClassNotFoundException;

    /**
     * Checks if a connection is established
     *
     * @return true if the connection is established
     *
     * @throws SQLException if the connection cannot be checked
     */
    public abstract boolean isConnectionOpen() throws SQLException;

    /**
     * Gets the connection with the database.
     *
     * @return Connection with the database, null if none
     */
    public abstract Connection getConnection() throws SQLException, ClassNotFoundException;

    /**
     * Closes the connection with the database.
     *
     * @return true if successful
     *
     * @throws SQLException if the connection cannot be closed
     */
    public abstract boolean closeConnection() throws SQLException;

    /**
     *
     * Creates a table that is required to store data
     *
     * @return True if the table creation was successful, false otherwise
     */
    public abstract boolean initialise();

    /**
     *
     * Queries the database
     *
     * @param query the query you want to execute
     * @return a playtime of a player
     */
    public abstract int queryPlayTime(String query);


    /**
     *
     * Queries the database with no result
     *
     * @param query the query you want to execute
     *
     */
    public abstract void queryVoid(String query);
    /**
     * Gets the database type
     *
     * @return Database type
     */
    public DatabaseType getDatabaseType() {
        return databaseType;
    }
}
