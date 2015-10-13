package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
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

    public void testEditATweet(){
        LonelyTwitterActivity activity = (LonelyTwitterActivity) getActivity();
        bodyText = activity.getBodyText();
        activity.getTweets().clear();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                bodyText.setText("hamburgers");
            }
        });

        getInstrumentation().waitForIdleSync();
        saveButton = activity.getSaveButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                saveButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        final ListView oldTweetsList= activity.getOldTweetsList();
        Tweet tweet = (Tweet) oldTweetsList.getItemAtPosition(0);
        assertEquals("hamburgers", tweet.getText());

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(EditTweetActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = oldTweetsList.getChildAt(0);
                oldTweetsList.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();


        // Set up an ActivityMonitor


// Validate that ReceiverActivity is started
//        TouchUtils.clickView(this, sendToReceiverButton);
        final EditTweetActivity receiverActivity = (EditTweetActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditTweetActivity.class, receiverActivity.getClass());

// Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);
//        Button saveEditButton = receiverActivity.getSaveEditButton();

        // test that tweet being shown on edit screen is the tweet we clicked on
        assertEquals(activity.getTweets().get(0), receiverActivity.getTweetString());
        // edit the text of that tweet
        receiverActivity.runOnUiThread(new Runnable() {
            public void run() {
                receiverActivity.setTweetString("Edited text!");
            }
        });

        // save our edits
        activity.editTweet(0, receiverActivity.getTweetString());
        // assert that our edits are saved into the tweet correctly.
        assertEquals(activity.getTweets().get(0), receiverActivity.getTweetString());
        activity.updateAdapter();
        // assert that our edits are shown on the screen to the user back in the main activity.
        assertEquals(activity.getArrayAdapter().getItem(0).toString(), receiverActivity.getTweetString());
        // end of test: clear the data.
        activity.getTweets().clear();
        // end of test: make sure the edit activity is closed
        receiverActivity.finish();
    }
}