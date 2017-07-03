package com.KyMann.EmperorTrump.Models.Controllers;

import com.KyMann.EmperorTrump.EmpTrumpDict;
import com.KyMann.EmperorTrump.Models.EmperorTweet;

import java.util.HashMap;

/**
 * Created by Kyle on 6/15/2017.
 */
public class EmperorControl {
//this holds the logic for the word by word translation
    //should be called by methodname?

    public EmperorTweet insertEmperorWords(String trumpTweet) {
        //should be called by method
        String emperorTweet = "";
        int changeCount = 0;
        HashMap<String, String> forwardsTranslationDictionary = EmpTrumpDict.buildForwardsHashMap();
        //TODO: emp trump dict is empty here
        for (String word : trumpTweet.split(" ")) { //TODO: parser on things other than space, remove punctuation
            if (forwardsTranslationDictionary.containsKey(word)) {
                emperorTweet = emperorTweet + " " + forwardsTranslationDictionary.get(word);
                changeCount++;
            }
            else {
                emperorTweet = emperorTweet + " " + word;
            }
        }

        return new EmperorTweet(emperorTweet, changeCount);
    }
}
