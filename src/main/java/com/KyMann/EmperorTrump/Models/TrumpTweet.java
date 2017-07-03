package com.KyMann.EmperorTrump.Models;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Kyle on 6/20/2017.
 */
public class TrumpTweet {

    private String tweet;

    @Autowired
    private int Id;

    public TrumpTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public int getId() { return Id;}

    public void setId(int Id) {this.Id = Id;}
}
