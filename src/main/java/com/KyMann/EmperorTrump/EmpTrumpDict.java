package com.KyMann.EmperorTrump;

import java.util.Dictionary;
import java.util.HashMap;


/**
 * Created by Kyle on 6/15/2017.
 */
public class EmpTrumpDict {
//this class holds the dictionary that will translate between Trump and EmperorTrump
    //should be accessed by calling this function

    public static HashMap<String, String> buildForwardsHashMap() {
        HashMap forwardsDictionary = new HashMap();
        //first batch
        forwardsDictionary.put("Trump", "Emperor"); //+2
        forwardsDictionary.put("clown", "fool"); //-1
        forwardsDictionary.put("tv", "holovid"); //+5
        forwardsDictionary.put("television", "holovid"); //-3
        forwardsDictionary.put("country", "sector"); //-1
        forwardsDictionary.put("state", "planet"); //+1
        forwardsDictionary.put("United States", "Galactic Empire"); //+2
        forwardsDictionary.put("Democrat", "Jedi"); //-4
        forwardsDictionary.put("great", "grand"); //0
        forwardsDictionary.put("nuke", "Death Star"); //+6
        forwardsDictionary.put("fake news", "rebel propaganda"); //+7
        forwardsDictionary.put("totally", "completely"); //+3
        forwardsDictionary.put("cabinet", "Joint Chiefs"); //
        forwardsDictionary.put("coal", "rhydonium");
        forwardsDictionary.put("oil", "hypermatter");
        forwardsDictionary.put("natural gas", "Peragian fuel");
        forwardsDictionary.put("Executive Orders", "contingency orders");
        forwardsDictionary.put("America", "the Empire");
        forwardsDictionary.put("Battlegroup", "flotilla");
        forwardsDictionary.put("Commander in chief", "Supreme Chancellor");
        forwardsDictionary.put("AMERICA", "the SITH");


        return forwardsDictionary;
    }

}