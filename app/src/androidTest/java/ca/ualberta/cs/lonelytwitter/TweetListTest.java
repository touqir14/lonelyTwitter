package ca.ualberta.cs.lonelytwitter;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by touqir on 9/29/15.
 */
public class TweetListTest extends ActivityInstrumentationTestCase2 implements MyObserver {
    public TweetListTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }

    public void testHoldsStuff() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("test");
        list.add(tweet);
        assertSame(list.getMostRecentTweet(), tweet);
    }

    public void testHoldsManyThings() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("test");
        list.add(tweet);
        assertEquals(list.count(), 1);
        list.add(new NormalTweet("test"));
        assertEquals(list.count(), 2);
    }

    public void testAddTweet() {
        TweetList list = new TweetList();
        Tweet tweet1=new NormalTweet("yo");
        list.add(tweet1);
        boolean gotException=false;
        try {
            list.addTweet(tweet1);
        }
        catch (IllegalArgumentException e){
            gotException=true;
        }
        assertEquals(gotException,true);
    }

    public void testGetTweets() {
        TweetList list = new TweetList();

        Tweet tweet1=new NormalTweet("yo");
        Tweet tweet2=new NormalTweet("no");
        Tweet tweet3=new NormalTweet("lol");

        ArrayList<Tweet> testTweets1=new ArrayList<Tweet>();
        ArrayList<Tweet> testTweets2=new ArrayList<Tweet>();
        ArrayList<Tweet> testTweets3=new ArrayList<Tweet>();

        testTweets1.add(tweet1);
        testTweets2.add(tweet1);
        testTweets2.add(tweet2);
        testTweets3.addAll(testTweets2);
        testTweets3.add(tweet3);

        list.addTweet(tweet1);
        ArrayList<Tweet> gotTweets1= list.getTweets();
        list.addTweet(tweet2);
        ArrayList<Tweet> gotTweets2= list.getTweets();
        list.addTweet(tweet3);
        ArrayList<Tweet> gotTweets3= list.getTweets();

        assertEquals(gotTweets1,testTweets1);
        assertEquals(gotTweets2,testTweets2);
        assertEquals(gotTweets3,testTweets3);
    }

    public void testHasTweet() {
        TweetList list = new TweetList();
        Tweet tweet1=new NormalTweet("yo");
        Tweet tweet2=new NormalTweet("no");
        list.add(tweet1);
        boolean contains1= list.hasTweet(tweet1);
        boolean contains2= list.hasTweet(tweet2);
        assertEquals(contains1,true);
        assertEquals(contains2,false);
    }

    public void testRemoveTweet() {
        TweetList list = new TweetList();
        Tweet tweet1=new NormalTweet("yo");
        Tweet tweet2=new NormalTweet("no");
        list.add(tweet1);
        boolean contains1= list.removeTweet(tweet1);
        boolean contains2= list.removeTweet(tweet2);
        assertEquals(contains1,true);
        assertEquals(contains2,false);
    }

    public void testGetCount() {
        TweetList list = new TweetList();
        Tweet tweet1=new NormalTweet("yo");
        Tweet tweet2=new NormalTweet("no");
        list.add(tweet1);
        int size1= list.getCount();
        list.add(tweet2);
        int size2= list.getCount();
        assertEquals(size1,1);
        assertEquals(size2,2);
    }

    private Boolean weWereNotified;

    public void myNotify(MyObservable observable){
        weWereNotified=Boolean.TRUE;
    }

    public void testObservable(){
        TweetList list= new TweetList();
        // needs an addObserver
        list.addObserver(this);
        Tweet tweet = new NormalTweet("test");
        weWereNotified=Boolean.FALSE;
        list.add(tweet);
        assertTrue(weWereNotified);
    }

    public void testModifyTweetInList(){
        TweetList list= new TweetList();
        // needs an addObserver
        list.addObserver(this);
        Tweet tweet = new NormalTweet("test");
        list.add(tweet);
        weWereNotified=Boolean.FALSE;
        tweet.setText("different text");
        assertTrue(weWereNotified);
    }
}