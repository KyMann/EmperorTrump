package com.KyMann.EmperorTrump;

import com.KyMann.EmperorTrump.Models.Controllers.EmperorControl;
import com.KyMann.EmperorTrump.Models.EmperorTweet;
import com.KyMann.EmperorTrump.Models.TrumpTweet;
import com.KyMann.EmperorTrump.Models.data.EmperorTweetsDao;
import com.KyMann.EmperorTrump.Models.data.TrumpTweetsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


@SpringBootApplication
public class EmperorTrumpApplication {

	//private static ArrayList<EmperorTweet> emperorTweetsArrayList = new ArrayList<EmperorTweet>();
	@Autowired
	private static EmperorTweetsDao emperorTweetsDao;
	private static TrumpTweetsDao trumpTweetsDao;
	private static Boolean streamOn = false;
	private static Boolean pushTweet = false;

	public static void main(String[] args) throws TwitterException {

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

		//twitter streaming input
		if (streamOn) {
			TwitterStream twitterStream = new TwitterStreamFactory(new ConfigurationBuilder().setJSONStoreEnabled(true).build()).getInstance();
			twitterStream.setOAuthConsumer(TwitterKeys.consumerKey, TwitterKeys.consumerSecret);
			twitterStream.setOAuthAccessToken(new AccessToken(TwitterKeys.accessTokenKey, TwitterKeys.accessTokenSecret));
			StatusListener listener = new StatusListener() {
				public void onStatus(Status status) {
					TrumpTweet tweet = new TrumpTweet(status.getText());
					trumpTweetsDao.save(tweet); //TODO: fix the database so that it will save
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
		for (int pageNum = 1; pageNum < 2; pageNum++) {
			Paging paging = new Paging(pageNum, 100);
			//.get is past tweets
			List<Status> status = twitter.getUserTimeline("@realDonaldTrump", paging);

			for (Status s : status) {
				TrumpTweet trumpTweet = new TrumpTweet(s.getText());
				System.out.println(trumpTweet);
				trumpTweetsDao.save(trumpTweet);
			}
		}

		//EmperorControl object needed to run conversion methods
		EmperorControl empTrumpTranslator = new EmperorControl();

		//for loop loops through past tweets here
		for(TrumpTweet trumpTweet : trumpTweetsDao.findAll()) {

			//this runs the converter - DOES NOT RETURN STRING BUT OBJECT
			EmperorTweet empTweet = empTrumpTranslator.insertEmperorWords(trumpTweet.getTweet());
			trumpTweetsDao.delete(trumpTweet.getId());

			//
			if (empTweet.getChanges() != 0) {
				//System.out.println(s.getUser().getName() + " " + s.getText());
				System.out.println(empTweet.getTweet() + ", " +  String.valueOf(empTweet.getChanges()));

				emperorTweetsDao.save(empTweet);
			}
		}

		//TODO:start up web page
		SpringApplication.run(EmperorTrumpApplication.class, args);

		//TODO: check occasionally and post(and remove from database) approved tweets using set interval
		if (pushTweet) {
		    TimerTask pushTweet = new TimerTask() {
                public void run() {pushTweet(twitter);}
            };
            Timer tweetTimer = new Timer();
            tweetTimer.schedule(pushTweet, 1, 7200000); //
        }

	}

    public static void pushTweet (Twitter twitter){
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