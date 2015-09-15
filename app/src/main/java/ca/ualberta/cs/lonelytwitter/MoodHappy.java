package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

/**
 * Created by satyabra on 9/15/15.
 */
public class MoodHappy extends CurrentMood {
    public MoodHappy() {
        super();
    }

    public MoodHappy(Date date) {
        super(date);
    }

    public String getMood() {
        return new String("(^-^)");
    }

}
