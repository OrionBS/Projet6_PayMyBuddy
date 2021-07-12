package fr.orionbs.PayMyBuddy.controller;

import fr.orionbs.PayMyBuddy.dto.UserSession;
import fr.orionbs.PayMyBuddy.mapper.UserMapping;
import fr.orionbs.PayMyBuddy.model.Friend;
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
public class FriendsController {

    @Autowired
    UserService userService;
    @Autowired
    UserMapping userMapping;

    @GetMapping(path = "/friends")
    public String friends(HttpSession httpSession, Model model) {
        Friend friend = new Friend();
        model.addAttribute("friend", friend);

        UserSession userSession = (UserSession) httpSession.getAttribute("user");
        if(userSession != null) {
            userSession = userMapping.userRepoToUserSession(userService.findUser(userSession.getEmailSession()));
            model.addAttribute("user", userSession);
            return "friends";
        }
        return "redirect:/login";
    }

    @PostMapping(path = "/friends")
    public String loginTreatment(@ModelAttribute(name = "friend") Friend friend, HttpSession httpSession){
        UserSession userSession = (UserSession) httpSession.getAttribute("user");

        if(userSession != null) {

            userService.addFriend(userSession.getEmailSession(), friend.getFriendEmail());
            return "redirect:/friends";
        }

        return "redirect:/";
    }
}
