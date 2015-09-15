package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

/**
 * Created by satyabra on 9/15/15.
 */

public class ImportantTweet extends Tweet {

    public ImportantTweet(Date date, String tweet) {
        super(date, tweet);
    }

    public ImportantTweet(String text) {
        super(text);
    }

    public Boolean isImportant() {
        return true;
    }

    @Override
    public String getText() {
        return "important: " + super.getText();
    }
}
