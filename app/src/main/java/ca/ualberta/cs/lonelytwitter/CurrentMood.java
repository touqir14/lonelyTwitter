package ca.ualberta.cs.lonelytwitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by touqir on 9/15/15.
 */
public abstract class CurrentMood {
    protected Date date;
    protected String defaultDate="2015-09-15";
    protected String defaultDateFormat="yyyy-mm-dd";
    protected String moodSignature;
    protected ArrayList<String> tweetList;

    protected CurrentMood(){
        try {
            this.date = parseDate(defaultDate,defaultDateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("failed to initialize CurrentMood.Match your date format with the default one");
        }
    }

    protected CurrentMood(Date date) {
        this.date = date;
    }

    protected String getMoodSignature()

    protected Date getDate() {
        return date;
    }

    protected void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<String> getTweetList() {
        return tweetList;
    }

    public void addTweetList(String tweets) {
        this.tweetList.add(tweets);
    }

    public static Date parseDate(String format, String inputDate) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat(format);

        try {
            return formatter.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("date given in incorrect format");
        }
    }
}
