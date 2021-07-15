package fr.orionbs.PayMyBuddy.controller;

import fr.orionbs.PayMyBuddy.dto.FriendDTO;
import fr.orionbs.PayMyBuddy.mapper.UserMapping;
import fr.orionbs.PayMyBuddy.model.UserSession;
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
        FriendDTO friendDTO = new FriendDTO();
        model.addAttribute("friendDTO", friendDTO);

        UserSession userSession = (UserSession) httpSession.getAttribute("userSession");
        if(userSession != null) {
            userSession = userMapping.userRepoToUserSession(userService.findUser(userSession.getEmailSession()));
            model.addAttribute("userSession", userSession);
            return "friends";
        }
        return "redirect:/login";
    }

    @PostMapping(path = "/friends")
    public String addingFriendTreatment(@ModelAttribute(name = "friendDTO") FriendDTO friendDTO, HttpSession httpSession){
        UserSession userSession = (UserSession) httpSession.getAttribute("userSession");

        if(userSession != null) {

            Boolean done = userService.addFriend(userSession.getEmailSession(), friendDTO.getUser().getEmail());
            return "redirect:/friends";
        }

        return "redirect:/";
    }
}
