package ca.ualberta.cs.lonelytwitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by joshua2 on 9/16/15.
 */
public abstract class Tweet implements Tweetable, Comparable<Tweet>, MyObserverable {
    private String text;
    protected Date date;

    public Tweet(String tweet, Date date) throws TweetTooLongException {
        this.setText(tweet);
        this.date = date;
    }

    public Tweet(String tweet) throws TweetTooLongException {
        this.setText(tweet);
        this.date = new Date();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) throws TweetTooLongException {
        if (text.length() <= 140) {
            this.text = text;
            notifAllObservers();
        } else {
            throw new TweetTooLongException();
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        notifAllObservers();
    }

    public abstract Boolean isImportant();

    @Override
    public String toString() {
        return date.toString() + " | " + text;
    }

    // taken from http://www.mkyong.com/java/java-object-sorting-example-comparable-and-comparator/
    public int compareTo(Tweet other) {
        return this.getDate().compareTo(other.getDate());
    }

    // volatile: tells anything that might be serializing this to e.g. disk, or network, wont need to be saved.
    // Tells that this attribute is not something that needs to be saved, e.g. GSON won't save this then.
    private volatile ArrayList<MyObserver> observers = new ArrayList<MyObserver>();

    public void addObserver(MyObserver observer) {
        observers.add(observer);
    }

    private void notifAllObservers() {
        for(MyObserver observer : observers) {
            observer.myNotify(this);
        }
    }

}
