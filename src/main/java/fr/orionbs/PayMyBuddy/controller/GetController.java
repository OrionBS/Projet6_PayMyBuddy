package fr.orionbs.PayMyBuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GetController {

    @GetMapping(path = "/")
    public String home() {
        return "home";
    }

    @GetMapping(path = "/transfer")
    public String transfer() {
        return "transfer";
    }

    @GetMapping(path = "/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping(path = "/friends")
    public String friends() {
        return "friends";
    }
}
