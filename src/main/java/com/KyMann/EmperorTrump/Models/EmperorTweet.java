package com.KyMann.EmperorTrump.Models;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class EmperorTweet {

    private String tweet;

    private int changes;

    private boolean approved;

    @Autowired
    @GeneratedValue
    private int Id;

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

    public int getId() {return Id;}

}
