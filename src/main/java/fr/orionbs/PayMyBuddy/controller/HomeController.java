package fr.orionbs.PayMyBuddy.controller;

import fr.orionbs.PayMyBuddy.model.UserSession;
import fr.orionbs.PayMyBuddy.mapper.UserMapping;
import fr.orionbs.PayMyBuddy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    UserService userService;
    @Autowired
    UserMapping userMapping;

    @GetMapping(path = "/")
    public String home(HttpSession httpSession, Model model) {
        UserSession userSession = (UserSession) httpSession.getAttribute("userSession");
        if(userSession != null) {
            userSession = userMapping.userRepoToUserSession(userService.findUser(userSession.getEmailSession()));
            model.addAttribute("userSession", userSession);
            return "home";
        }
        return "redirect:/login";
    }

}
