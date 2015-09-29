package ca.ualberta.cs.lonelytwitter;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by satyabra on 9/29/15.
 */
public class TweetListTest extends ActivityInstrumentationTestCase2 {
    public TweetListTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }

    public void testHoldStuff() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("Aaah it'sa test");
        list.add(tweet);
        assertSame(list.getMostRecentTweet(), tweet);
    }

    public void testHoldManyStuff() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("Aaah it'sa test");
        list.add(tweet);
        assertEquals(list.getCount(), 1);
        list.add(new NormalTweet("test"));
        assertEquals(list.getCount(), 2);
    }

    public void testGetTweets() {
        // getTweets() -- sould return a list of tweets in chronological order
        TweetList list = new TweetList();
        Tweet tweet1 = new NormalTweet("aaaa");
        Tweet tweet2 = new NormalTweet("aaaaaaaa");
        Date date = new Date(12, 11, 24);
        Tweet tweet3 = new NormalTweet("aa", date);

        list.add(tweet1);
        list.add(tweet2);
        list.add(tweet3);

        ArrayList<Tweet> a = list.getTweets();
        assertSame(a.get(0), tweet3);

    }

    public void testHasTweet() {
        // hasTweet() -- should return true if there is a tweet that equals() another tweet
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("aaaa");
        list.add(tweet);
        assertTrue(list.hasTweet(tweet));
    }

    public void testRemoveTweet() {
        // removeTweet() -- should remove a tweet
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("aaaa");
        list.add(tweet);
        list.removeTweet(tweet);
        assertFalse(list.hasTweet(tweet));
    }

}