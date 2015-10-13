package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import junit.framework.TestCase;

/**
 * Created by wz on 14/09/15.
 */
public class LonelyTwitterActivityTest extends ActivityInstrumentationTestCase2 {

    private EditText bodyText;
    private Button saveButton;

    public LonelyTwitterActivityTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    public void testEditATweet() {
        // starts lonelyTwitter
        LonelyTwitterActivity activity = (LonelyTwitterActivity) getActivity();

        //reset the app to a known state
        activity.getTweets().clear();

        //user clicks on tweet they want to edit:

        // basic way to pretend you're the user: simulate user creating a tweet, some way to get the edit text view.
        // Can use FindViewById or make a getter for it.
        bodyText = activity.getBodyText();

        // running on the thread of the lonelyTiwterActivity
        //bodyText.setText("all your cheezburgers are belong to us.");
        activity.runOnUiThread(new Runnable() {
            public void run() {
               bodyText.setText("all your cheezburgers are belong to us.");
           }
        });

        // act just like the user clicked on the button:
        //saveButton.performClick();
        saveButton = activity.getSaveButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                saveButton.performClick();
            }
        });

        // wait for the stuff to sync up from activity and test:
        // make sure our UI thread finishes.
        getInstrumentation().waitForIdleSync();

        final ListView oldTweetsList = activity.getOldTweetsList();
        Tweet tweet = (Tweet) oldTweetsList.getItemAtPosition(0);
        assertEquals("all your cheezburgers are belong to us.", tweet.getText());

        // add a tweet editing function. And edit a tweet when you click on it:

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(EditTweetActivity.class.getName(),
                        null, false);

        saveButton = activity.getSaveButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = oldTweetsList.getChildAt(0);
                oldTweetsList.performItemClick(v, 0, v.getId());
                //Imagine we have a new window pop-up to edit the tweet.

            }
        });

        getInstrumentation().waitForIdleSync(); // make sure our UI thread finished


        // Following was stolen from https://developer.android.com/training/activity-testing/activity-functional-testing.html 2015-10-13



        // Validate that ReceiverActivity is started
        EditTweetActivity receiverActivity = (EditTweetActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditTweetActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // assert test that the tweet being shown on the edit screen is the tweet we clicked on
        tweet = (Tweet) oldTweetsList.getItemAtPosition(0);
        assertEquals(tweet.getText(), receiverActivity.getEditTxt());

        // edit the text of that tweet
        

        // save our edits

        // assert that our edits were saved into the tweet correctly

        //assert that our edits are shown on the screen to the user back in the main activity

        // end of test: clear the data
        // end of test: make sure the edit activity is closed
        receiverActivity.finish();
    }

}