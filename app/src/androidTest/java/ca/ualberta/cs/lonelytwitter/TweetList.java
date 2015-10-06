package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;

/**
 * Created by touqir on 9/29/15.
 */
public class TweetList implements MyObservable, MyObserver{

    private Tweet mostRecentTweet;
    private ArrayList<Tweet> tweets=new ArrayList<Tweet>();

    public void add(Tweet tweet){
//        mostRecentTweet=tweet;
        tweets.add(tweet);
        tweet.addObserver(this);
        notifyAllObservers();
//        mostRecentTweet=null;
    }

    public Tweet getMostRecentTweet(){
        return mostRecentTweet;
    }

    public int count(){
        return tweets.size();
    }

    public void addTweet(Tweet tweet){ // -- should throw an IllegalArgumentException when one tries to add a duplicate tweet
        if (tweets.contains(tweet)==true){
            throw new IllegalArgumentException();
        }
        else {
            tweets.add(tweet);
        }
    }

    public ArrayList<Tweet> getTweets() { //-- should return a list of tweets in chronological order
        return tweets;
    }

    public boolean hasTweet(Tweet tweet) { // -- should return true if there is a tweet that equals() another tweet
        return tweets.contains(tweet);
    }
//
    public boolean removeTweet(Tweet tweet) { // -- should remove a tweet
        int tweetIndex=tweets.indexOf(tweet);
        if (tweetIndex==-1){
            return false;
        }
        tweets.remove(tweetIndex);
        return true;
    }
//
    public int getCount() { //-- should accurately count up the tweets
        return tweets.size();
    }

    private volatile ArrayList<MyObserver> observers = new ArrayList<MyObserver>();

    public void addObserver(MyObserver observer){
        observers.add(observer);
    }

    private void notifyAllObservers(){
        for (MyObserver observer: observers){
            observer.myNotify(this);
        }
    }

    public void myNotify(MyObservable observable){
        notifyAllObservers();
    }
}
