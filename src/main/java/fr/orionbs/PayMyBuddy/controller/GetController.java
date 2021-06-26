package fr.orionbs.PayMyBuddy.controller;

import fr.orionbs.PayMyBuddy.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
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

    @GetMapping(path = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(path = "/registration")
    public String registration(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }
    @PostMapping(path = "/registerForm")
    public String submitForm(@ModelAttribute(name = "user") User user) {
        log.info("New User : "+user);
        return "home";
    }

    @GetMapping(path = "/logoff")
    public String logoff() {
        return "login";
    }
}
