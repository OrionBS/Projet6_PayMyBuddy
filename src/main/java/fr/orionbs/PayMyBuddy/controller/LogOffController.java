package fr.orionbs.PayMyBuddy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class LogOffController {

    @GetMapping(path = "/logoff")
    public String logoff(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        return "redirect:/login";
    }
}
