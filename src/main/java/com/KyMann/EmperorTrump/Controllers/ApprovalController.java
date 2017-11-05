package com.KyMann.EmperorTrump.Controllers;

import com.KyMann.EmperorTrump.Models.EmperorTweet;
import com.KyMann.EmperorTrump.Models.data.EmperorTweetsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Kyle on 6/20/2017.
 */
@Controller
@RequestMapping("Tweet")
public class ApprovalController {

    @Autowired
    private EmperorTweetsDao emperorTweetsDao;

    @RequestMapping(value= "Approval")
    public String index (Model model) {

        model.addAttribute("tweets", emperorTweetsDao.findAll()); //TODO: paginate results for performance
        model.addAttribute("title", "Approve these Tweets");

        return "Tweet/Approval";
    }

    @RequestMapping(value = "Edit/{id}", method= RequestMethod.GET)
    public String editTweet (Model model, @PathVariable long id) {

        EmperorTweet editedTweet = emperorTweetsDao.findOne((int)id);

        model.addAttribute("title", "Edit this Tweet");

        return "Tweet/Edit";
    }

    @RequestMapping(value = "Edit", method = RequestMethod.POST)
    public String processEditTweet (Model model, String tweet, int tweetId) {

        EmperorTweet editedTweet = emperorTweetsDao.findOne(tweetId);
        editedTweet.setTweet(tweet);

        emperorTweetsDao.save(editedTweet);

        return "Tweet/Approval";
    }
}
