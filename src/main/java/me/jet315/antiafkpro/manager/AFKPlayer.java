package me.jet315.antiafkpro.manager;

import me.jet315.antiafkpro.utils.MouseLocation;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AFKPlayer {


    private Player player;
    private UUID playersUUID;
    private int secondsPlayed;
    private MouseLocation lastMouseLocation;

    private int secondsAFK = 0;


    public AFKPlayer(Player player, UUID playersUUID, int secondsPlayed) {
        this.player = player;
        this.playersUUID = playersUUID;
        this.secondsPlayed = secondsPlayed;
    }

    public AFKPlayer(Player player, int secondsPlayed) {
        this.player = player;
        this.playersUUID = player.getUniqueId();
        this.secondsPlayed = secondsPlayed;
    }

    /**
     *
     * @return The player object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @return The players UUID
     */
    public UUID getPlayersUUID() {
        return playersUUID;
    }

    /**
     *
     * @return The amount of seconds the player has played for
     */
    public int getSecondsPlayed() {
        return secondsPlayed;
    }

    /**
     *
     * @return The amount of minutes the player has played for
     */
    public int getMinutesPlayed(){
        return secondsPlayed/60;
    }
    /**
     *
     * @return The amount of hours the player has played for
     */
    public int getHoursPlayed(){
        return secondsPlayed/3600;
    }
    /**
     *
     * @return The amount of days the player has played for
     */
    public int getDaysPlayed(){
        return getHoursPlayed()/24;
    }

    /**
     *
     * @return A 4 element array with Days, Hours, Minutes and Seconds the user has played for
     */
    public int[] getPlaytime(boolean formated){
        int seconds = secondsPlayed;
        int[] timePlayed = new int[4];

        //Checks to make sure time exists
        timePlayed[0] = ((int) (seconds / 86400 ));
        timePlayed[1] = (int) seconds/3600 - ( timePlayed[0] *24) ;
        timePlayed[2] =(int) seconds/60 - (( timePlayed[1] * 60) + (timePlayed[0]*1440));
        timePlayed[3] = (int) seconds - ((timePlayed[2]*60) + (timePlayed[1]*3600) + (timePlayed[0]*86400));

        return timePlayed;
    }

    /**
     *
     * @param secondsPlayed Set the seconds the player has played for
     */
    public void setSecondsPlayed(int secondsPlayed) {
        this.secondsPlayed = secondsPlayed;
    }


    /**
     *
     * @return The last mouse location the user was at
     */
    public MouseLocation getLastMouseLocation() {
        return lastMouseLocation;
    }

    /**
     *
     * @param lastMouseLocation The mouse location you want to set the players last mouse location as
     */
    public void setLastMouseLocation(MouseLocation lastMouseLocation) {
        this.lastMouseLocation = lastMouseLocation;
    }


    /**
     *
     * @return The seconds the user has been afk for
     */
    public int getSecondsAFK() {
        return secondsAFK;
    }

    /**
     *
     * @param secondsAFK The seconds the player has been afk for
     */
    public void setSecondsAFK(int secondsAFK) {
        this.secondsAFK = secondsAFK;
    }

}
