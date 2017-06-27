package com.KyMann.EmperorTrump.Models;

/**
 * Created by Kyle on 6/15/2017.
 */
public class EmperorTweet {

    private String tweet;

    private int changes;

    private boolean approved;

    public EmperorTweet() {}

    public EmperorTweet(String aTweet, int aChanges) {
        super();
        tweet = aTweet;
        changes = aChanges;
        approved = false;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet; }

    public int getChanges() {
        return changes;
    }

    public void setChanges(int changes) {
        this.changes = changes;
    }

    public boolean isApproved() {return approved;}
}
