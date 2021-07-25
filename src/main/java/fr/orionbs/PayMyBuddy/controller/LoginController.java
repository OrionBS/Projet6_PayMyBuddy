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
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    UserMapping userMapping = new UserMapping();

    @GetMapping(path = "/login")
    public String login(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO",userDTO);
        return "login";
    }
    @PostMapping(path = "/login")
    public String loginTreatment(@ModelAttribute(name = "userDTO") UserDTO userDTO, HttpSession httpSession) {

        // Need Boolean if user is register
        if (userService.findUserWithEmailAndPassword(userDTO.getEmail(), userDTO.getPassword())) {

            // If Register, get his informations
            UserSession userSession = userMapping.userRepoToUserSession(userService.findUser(userDTO.getEmail()));
            httpSession.setAttribute("userSession", userSession);

            return "redirect:/";
        }

        return "registration";
    }
}
