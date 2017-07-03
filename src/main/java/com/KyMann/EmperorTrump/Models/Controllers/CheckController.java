package com.KyMann.EmperorTrump.Models.Controllers;

import com.KyMann.EmperorTrump.Models.data.EmperorTweetsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Kyle on 6/20/2017.
 */
public class CheckController {

    @Autowired
    private EmperorTweetsDao emperorTweetsDao;

    @RequestMapping(value= "CheckWords")
    public String index (Model model) {

        //model.addAttribute();

        return "EmperorTrump/CheckWords";
    }
}
