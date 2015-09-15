package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

/**
 * Created by touqir on 9/15/15.
 */
public class HappyMood extends CurrentMood{


    this.moodSignature=":)";
    
    @Override
    protected String getMoodSignature() {
        return this.moodSignature;
    }

    public HappyMood(Date date) {
        super(date);
    }

    public HappyMood() {
    }



}
