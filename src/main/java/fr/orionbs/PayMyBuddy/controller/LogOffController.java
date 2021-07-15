package fr.orionbs.PayMyBuddy.controller;

import fr.orionbs.PayMyBuddy.model.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class LogOffController {

    @GetMapping(path = "/logoff")
    public String logoff(HttpSession httpSession) {

        UserSession userSession = (UserSession) httpSession.getAttribute("userSession");
        if (userSession != null) {
            httpSession.removeAttribute("userSession");
            return "redirect:/login";
        }
        return "redirect:/login";
    }
}
