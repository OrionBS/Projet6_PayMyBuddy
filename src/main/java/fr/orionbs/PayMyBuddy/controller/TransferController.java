package fr.orionbs.PayMyBuddy.controller;

import fr.orionbs.PayMyBuddy.dto.TransactionDTO;
import fr.orionbs.PayMyBuddy.model.UserSession;
import fr.orionbs.PayMyBuddy.mapper.UserMapping;
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
    UserMapping userMapping = new UserMapping();

    @GetMapping(path = "/transfer")
    public String transfer(HttpSession httpSession, Model model) {
        TransactionDTO transactionDTO = new TransactionDTO();
        model.addAttribute("transactionDTO", transactionDTO);

        UserSession userSession = (UserSession) httpSession.getAttribute("userSession");

        if(userSession != null) {
            userSession = userMapping.userRepoToUserSession(userService.findUser(userSession.getEmailSession()));
            model.addAttribute("userSession", userSession);
            return "transfer";
        }
        return "redirect:/login";
    }
    @PostMapping(path = "/friendTransfer")
    public String friendTransfered(@ModelAttribute(name = "transactionDTO") TransactionDTO transactionDTO, HttpSession httpSession){

        UserSession userSession = (UserSession) httpSession.getAttribute("userSession");

        if(userSession != null) {
            transactionService.userToUser(userSession.getEmailSession(),transactionDTO.getCollectorEmail(),transactionDTO.getAmount(),transactionDTO.getDescription());
            return "redirect:/transfer";
        }

        return "redirect:/";
    }
    @PostMapping(path = "/bankTransfer")
    public String bankTransfered(@ModelAttribute(name = "transactionDTO") TransactionDTO transactionDTO, HttpSession httpSession){

        UserSession userSession = (UserSession) httpSession.getAttribute("userSession");

        if(userSession != null) {
            switch (transactionDTO.getTypeOfTransaction()) {
                case BankToUser:
                    transactionService.bankToUser(userSession.getEmailSession(),transactionDTO.getAmount());
                    return "redirect:/transfer";
                case UserToBank:
                    transactionService.userToBank(userSession.getEmailSession(),transactionDTO.getAmount());
                    return "redirect:/transfer";
            }
        }

        return "redirect:/";
    }
}
