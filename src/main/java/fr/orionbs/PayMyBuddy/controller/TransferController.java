package fr.orionbs.PayMyBuddy.controller;

import fr.orionbs.PayMyBuddy.dto.UserSession;
import fr.orionbs.PayMyBuddy.mapper.UserMapping;
import fr.orionbs.PayMyBuddy.model.Transaction;
import fr.orionbs.PayMyBuddy.model.TypeOfTransaction;
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
    public String friendTransfered(@ModelAttribute(name = "transaction") Transaction transaction,
                                   HttpSession httpSession){

        UserSession userSession = (UserSession) httpSession.getAttribute("user");

        if(userSession != null) {
            transactionService.UserToUser(userSession.getEmailSession(),transaction.getCollector().getEmail(),transaction.getAmount());
            return "redirect:/transfer";
        }

        return "redirect:/";
    }
    @PostMapping(path = "/bankTransfer")
    public String bankTransfered(@ModelAttribute(name = "transaction") Transaction transaction, HttpSession httpSession){

        UserSession userSession = (UserSession) httpSession.getAttribute("user");

        if(userSession != null) {
            switch (transaction.getTypeOfTransaction()) {
                case BankToUser:
                    transactionService.BankToUser(userSession.getEmailSession(),transaction.getAmount());
                    return "redirect:/transfer";
                case UserToBank:
                    transactionService.UserToBank(userSession.getEmailSession(),transaction.getAmount());
                    return "redirect:/transfer";
            }
        }

        return "redirect:/";
    }
}
