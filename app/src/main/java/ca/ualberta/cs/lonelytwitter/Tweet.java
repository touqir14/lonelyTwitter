package ca.ualberta.cs.lonelytwitter;

import java.io.IOException;
import java.util.Date;

/**
 * Created by touqir on 9/15/15.
 */
public abstract class Tweet implements Tweetable {

    private String text;
    private Date date;

    public String getText() {
        return this.text;
    }

    public void setText(String text) throws IOException{
        if (text.length()<=140) {
            this.text = text;
        } else {
            throw new IllegalArgumentException("Too longs Tweets BOY!");
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;

    }

    public Tweet(String tweet) {

        this.text=tweet;
        this.date=new Date();
    }

    public Tweet(String tweet, Date date) {

        this.text=tweet;
        this.date=date;
    }
}
