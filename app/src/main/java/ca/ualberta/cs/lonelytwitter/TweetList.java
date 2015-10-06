package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by satyabra on 9/29/15.
 */
public class TweetList implements MyObserverable, MyObserver {

    private Tweet mostRecentTweetThing;
    private ArrayList<Tweet> tweetList = new ArrayList<Tweet>();

    public void add(Tweet tweet) {
        mostRecentTweetThing = tweet;
        tweetList.add(tweet);
        tweet.addObserver(this);
        notifAllObservers();
    }

    public Tweet getMostRecentTweet() {
        return mostRecentTweetThing;
    }

    public int getCount() {
        return tweetList.size();
    }

    public ArrayList<Tweet> getTweets() {

        // taken from http://tutorials.jenkov.com/java-collections/sorting.html
        Comparator comparator = new Comparator() {
            public int compare(Object lhs, Object rhs) {
                Tweet llhs = (Tweet) lhs;
                Tweet rrhs = (Tweet) rhs;
                return llhs.getDate().compareTo(rrhs.getDate());
            }
        };

        Collections.sort(tweetList, comparator);
        return tweetList;
    }

    public boolean hasTweet(Tweet tweet) {
        return tweetList.contains(tweet);
    }

    public void removeTweet(Tweet tweet) {
        tweetList.remove(tweet);
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

    // Nested situation here. List will watching all the tweets inside it.
    public void myNotify(MyObserverable observable) {
        notifAllObservers();
    }

}
