package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

/**
 * Created by satyabra on 9/15/15.
 */
public class MoodSad extends CurrentMood {
    public MoodSad() {
        super();
    }

    public MoodSad(Date date) {
        super(date);
    }

    public String getMood() {
        return new String("(T-T)");
    }
}
