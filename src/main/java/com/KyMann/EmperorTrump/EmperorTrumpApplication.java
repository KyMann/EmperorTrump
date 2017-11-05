package com.KyMann.EmperorTrump;


import com.KyMann.EmperorTrump.Models.EmperorTweet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import twitter4j.*;

import twitter4j.conf.ConfigurationBuilder;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class EmperorTrumpApplication {
	//private static ApplicationContext context = new ClassPathXmlApplicationContext("SpringEmperorTrump.xml"); //cannot instantiate beans manually when they are hibernate interfaces
	//private static EmperorTweetsDao emperorTweetsDao = (EmperorTweetsDao) context.getBean("EmperorTweetsDaoBean");


    public static void main(String[] args) throws TwitterException {
		SpringApplication.run(EmperorTrumpApplication.class, args);
	}

}