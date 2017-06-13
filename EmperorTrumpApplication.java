package com.KyMann.EmperorTrump;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;


@SpringBootApplication
public class EmperorTrumpApplication {

	public static void main(String[] args) throws TwitterException {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey("1PmXuppMVlMdz4O2u1Fjk76tD")
                .setOAuthConsumerSecret("dXlUnXsKkwTyCjguVyHzCA3unRulV7fxG3flncsFZJC3miCT1Q")
                .setOAuthAccessToken("1758677539-OZiWn1VgJwhtedqLNVnrGAg3mJthneFtmqt2jT5")
                .setOAuthAccessTokenSecret("zMNoj5n4hZ9CotaQyXlMqVKNR0ZDBvrjgw7elRvCldH2q");

        TwitterFactory tf = new TwitterFactory(configurationBuilder.build());
        twitter4j.Twitter twitter = tf.getInstance();

        int pageNum = 1;
        Paging paging = new Paging(pageNum,100);

        List<Status> status=twitter.getUserTimeline("@realDonaldTrump", paging);
        for(Status s:status) {
            System.out.println(s.getUser().getName() + " " + s.getText());
        }
    }


}
