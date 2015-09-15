package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by satyabra on 9/15/15.
 */

public abstract class Tweet implements Tweetable {
    private String text;
    private Date date;
    private ArrayList<CurrentMood> currentMoods;

    // Constructor.
    public Tweet(Date date, String tweet) {
        this.date = date;
        this.text = tweet; // "this." is implied if text is unique. Thus in this case, you don't need to put "this." as a prefix.
    }

    // Constructor with default date.
    public Tweet(String text) {
        this.text = text;
        // set date to current date and time
        this.date = new Date();
    }

    //A way for each tweet to have a list of moods.
    public void addMood(CurrentMood newMood) {
        currentMoods.add(newMood);
    }

    public String listAllMoods() {
        String returnList = new String();
        for(CurrentMood each : currentMoods) {
            returnList += each.getMood();
        }
        return returnList + "-- End of all moods";
    }

    public void clearAllMoods() {
        currentMoods.clear();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
            if (text.length() <= 140)
                this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public abstract Boolean isImportant();

}
