package models.actions;

/**
 * Created by chrisjluc on 14-11-06.
 */
public class Action {
    public static final String START = "start";
    public static final String DRIVE = "drive";
    public static final String PICKUP = "pickup";
    public static final String DROPOFF = "dropoff";

    public int id = -1;
    public String action = "";
    public int x = -1;
    public int y = -1;
}
