package fr.orionbs.PayMyBuddy.controller;

import fr.orionbs.PayMyBuddy.dto.UserDTO;
import fr.orionbs.PayMyBuddy.model.UserSession;
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
public class ProfileController {

    @Autowired
    UserService userService;
    @Autowired
    UserMapping userMapping;

    @GetMapping(path = "/profile")
    public String profile(HttpSession httpSession, Model model) {

        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO",userDTO);

        UserSession userSession = (UserSession) httpSession.getAttribute("userSession");

        if(userSession != null) {
            userSession = userMapping.userRepoToUserSession(userService.findUser(userSession.getEmailSession()));
            model.addAttribute("userSession", userSession);
            return "profile";
        }

        return "redirect:/login";
    }

    @PostMapping(path = "/profile")
    public String updateProfile(@ModelAttribute(name = "userDTO") UserDTO userDTO, HttpSession httpSession){

        UserSession userSession = (UserSession) httpSession.getAttribute("userSession");

        if(userSession != null) {
            userDTO.setEmail(userSession.getEmailSession());
            userService.updateUserDTO(userDTO);
            return "redirect:/profile";
        }

        return "redirect:/";
    }
}
