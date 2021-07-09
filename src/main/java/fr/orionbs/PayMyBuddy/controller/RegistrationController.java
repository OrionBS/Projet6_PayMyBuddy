package fr.orionbs.PayMyBuddy.controller;

import fr.orionbs.PayMyBuddy.dto.UserSession;
import fr.orionbs.PayMyBuddy.mapper.UserMapping;
import fr.orionbs.PayMyBuddy.model.User;
import fr.orionbs.PayMyBuddy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapping userMapping;

    @GetMapping(path = "/registration")
    public String registration(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }
    @PostMapping(path = "/registration")
    public String registrationTreatment(@ModelAttribute(name = "user") User user, HttpSession httpSession) {
        log.info("New User : "+user);

        if (userService.addUser(user)) {
            UserSession userSession = userMapping.userRepoToUserSession(user);

            httpSession.setAttribute("user",userSession);

            return "redirect:/";
        }

        return "registration";
    }
}
