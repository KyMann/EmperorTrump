package com.KyMann.EmperorTrump.Controllers;

import com.KyMann.EmperorTrump.Models.EmperorTweet;
import com.KyMann.EmperorTrump.Models.data.EmperorTweetsDao;
import com.KyMann.EmperorTrump.TwitterKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 11/4/2017.
 */
@Controller
public class ScrapeTweetsController {


    boolean streamOn = false;
    @Autowired
    EmperorTweetsDao emperorTweetsDao;
    private static List<Status> trumpTweetsList = new ArrayList<Status>();

    @Scheduled(fixedRate=86400000) //1 day
    private void importData() throws TwitterException {
        //Config builder sets up twitter communications
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey(TwitterKeys.consumerKey)
                .setOAuthConsumerSecret(TwitterKeys.consumerSecret)
                .setOAuthAccessToken(TwitterKeys.accessTokenKey)
                .setOAuthAccessTokenSecret(TwitterKeys.accessTokenSecret);
        //twitter factory is the java lib object for twitter4java
        TwitterFactory tf = new TwitterFactory(configurationBuilder.build());
        twitter4j.Twitter twitter = tf.getInstance();

        //EmperorControl object needed to run conversion methods
        EmperorControl empTrumpTranslator = new EmperorControl();

        //twitter streaming input
        if (streamOn) {
            TwitterStream twitterStream = new TwitterStreamFactory(new ConfigurationBuilder().setJSONStoreEnabled(true).build()).getInstance();
            twitterStream.setOAuthConsumer(TwitterKeys.consumerKey, TwitterKeys.consumerSecret);
            twitterStream.setOAuthAccessToken(new AccessToken(TwitterKeys.accessTokenKey, TwitterKeys.accessTokenSecret));
            StatusListener listener = new StatusListener() {
                public void onStatus(Status status) {
                    trumpTweetsList.add(status);
                }

                public void onStallWarning(StallWarning stallWarning) {
                }

                public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                }

                public void onScrubGeo(long longitund, long latitude) {
                }

                public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                }

                public void onException(Exception ex) {
                    ex.printStackTrace();
                }
            };

            twitterStream.addListener(listener);
            FilterQuery query = new FilterQuery();
            query.follow(new long[]{25073877});
            twitterStream.filter(query);
        }

        //pageNum controls pages back into tweet history, might need to increment at some point, certain how to prevent repeats as tweets move back through the list
        //count is how many tweets per page, max 100
        //old tweets database built when server is started, //TODO: check to keep out duplicates when server is restarted
        if (trumpTweetsList.isEmpty()) {
            for (int pageNum = 1; pageNum < 2; pageNum++) {
                Paging paging = new Paging(pageNum, 100); //Trump tweets 12 times a day
                //.get is past tweets
                trumpTweetsList = twitter.getUserTimeline("@realDonaldTrump", paging);
            }

            for (Status s : trumpTweetsList) {
                //this runs the converter - DOES NOT RETURN STRING BUT OBJECT
                EmperorTweet empTweet = empTrumpTranslator.insertEmperorWords(s.getText());
                if (empTweet.getChanges() != 0) {
                    //System.out.println(s.getUser().getName() + " " + s.getText());
                    System.out.println(empTweet.getTweet() + ", " +  String.valueOf(empTweet.getChanges()));

                    emperorTweetsDao.save(empTweet);
                    trumpTweetsList.clear();
                }
            }
        }
    }

    @Scheduled(fixedRate=7200000)
    public void pushTweet (Twitter twitter){
        //this is it's own method, called by a timer object in main
        for (EmperorTweet tweet : emperorTweetsDao.findAll()) {
            if (tweet.isApproved()) {
                try {
                    twitter.updateStatus(tweet.getTweet());
                    emperorTweetsDao.delete(tweet.getId());
                } catch (TwitterException e) {
                    System.err.println("Error occurred while posting status!");
                }

            }
        }
    }
}
