package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

/**
 * Created by touqir on 9/15/15.
 */
public class NormalTweet extends Tweet {

    public NormalTweet(String tweet) {
        super(tweet);
    }

    public NormalTweet(String tweet, Date date) {
        super(tweet, date);
    }
}
