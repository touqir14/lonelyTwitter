package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

/**
 * Created by touqir on 9/15/15.
 */
public class SadMood extends CurrentMood {

    this.moodSignature=":(";

    @Override
    protected String getMoodSignature() {
        return this.moodSignature;
    }

    public SadMood() {
    }

    public SadMood(Date date) {
        super(date);
    }
}
