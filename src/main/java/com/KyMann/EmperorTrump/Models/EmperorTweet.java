package com.KyMann.EmperorTrump.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class EmperorTweet {

    private String tweet;

    private int changes;

    private boolean approved;

    @GeneratedValue
    @javax.persistence.Id
    private int Id;

    public EmperorTweet() {}

    public EmperorTweet(String aTweet, int aChanges) {
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

    public void setApproved(boolean approved) { this.approved = approved;}

    public int getId() {return Id;}

    public void setId(int id) {this.Id = id;}

}
