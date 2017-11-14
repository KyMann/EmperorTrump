package com.KyMann.EmperorTrump.Controllers;

import com.KyMann.EmperorTrump.Models.data.EmperorTweetsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by KyleLaptop on 11/10/2017.
 */
@Controller
@RequestMapping("/")
public class ViewController {

    @Autowired
    private EmperorTweetsDao emperorTweetsDao;

    @RequestMapping(value="watch", method = RequestMethod.GET)
    public String viewtweets(Model model) {

        model.addAttribute("tweets", emperorTweetsDao.findAll());

        return "watch.html";
    }
}
