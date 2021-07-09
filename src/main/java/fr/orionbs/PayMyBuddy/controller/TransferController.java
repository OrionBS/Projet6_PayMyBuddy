package fr.orionbs.PayMyBuddy.controller;

import fr.orionbs.PayMyBuddy.dto.UserSession;
import fr.orionbs.PayMyBuddy.mapper.UserMapping;
import fr.orionbs.PayMyBuddy.model.Friend;
import fr.orionbs.PayMyBuddy.model.Transaction;
import fr.orionbs.PayMyBuddy.model.User;
import fr.orionbs.PayMyBuddy.service.TransactionService;
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
public class TransferController {

    @Autowired
    UserService userService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    UserMapping userMapping;

    @GetMapping(path = "/transfer")
    public String transfer(HttpSession httpSession, Model model) {
        Transaction transaction = new Transaction();
        model.addAttribute("transaction", transaction);
        UserSession userSession = (UserSession) httpSession.getAttribute("user");
        if(userSession != null) {
            userSession = userMapping.userRepoToUserSession(userService.findUser(userSession.getEmailSession()));
            model.addAttribute("user", userSession);
            return "transfer";
        }
        return "redirect:/login";
    }
    @PostMapping(path = "/friendTransfer")
    public String loginTreatment(@ModelAttribute(name = "transaction") Transaction transaction, HttpSession httpSession){
        Object userSessionObject = httpSession.getAttribute("user");
        User userSession = (User) userSessionObject;
        if(userSession != null) {
            transactionService.UserToUser(userSession.getEmail(),transaction.getCollector().getEmail(),transaction.getAmount());
            return "redirect:/transfer";
        }

        return "redirect:/";
    }
}
