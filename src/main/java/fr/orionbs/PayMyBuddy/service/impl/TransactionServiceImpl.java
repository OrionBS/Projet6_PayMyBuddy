package fr.orionbs.PayMyBuddy.service.impl;

import fr.orionbs.PayMyBuddy.model.BankLog;
import fr.orionbs.PayMyBuddy.model.Transaction;
import fr.orionbs.PayMyBuddy.model.TypeOfTransaction;
import fr.orionbs.PayMyBuddy.model.User;
import fr.orionbs.PayMyBuddy.repository.BankLogsRepository;
import fr.orionbs.PayMyBuddy.repository.UserRepository;
import fr.orionbs.PayMyBuddy.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Transactional
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BankLogsRepository bankLogsRepository;

    @Override
    public void userToUser(String emailUserSender, String emailUserCollector, float amount, String description) {
        log.info("Service UserToUser:");

        String dateNow = LocalDate.now().toString();

        User sender = userRepository.findByEmail(emailUserSender);
        User collector = userRepository.findByEmail(emailUserCollector);

        BankLog bankLog = BankLog
                .builder()
                .typeOfTransaction(TypeOfTransaction.Commission)
                .date(dateNow)
                .amount(amount*0.05f)
                .description("Commission sur transaction entre "+emailUserSender+" et "+emailUserCollector+".")
                .build();

        bankLogsRepository.save(bankLog);

        log.info("BankLog Commission rédigé et envoyé :"+bankLog);

        Transaction transaction = Transaction
                .builder()
                .typeOfTransaction(TypeOfTransaction.UserToUser)
                .amount(amount*0.95f)
                .date(dateNow)
                .description(description)
                .collector(collector)
                .sender(sender)
                .build();

        log.info("Transaction générée : " + transaction);

        sender.setAmount(sender.getAmount() - amount);
        sender.getTransactions().add(transaction);
        userRepository.save(sender);

        log.info("Envoyeur récupéré et mis à jour : " + sender.getFullName());

        collector.setAmount(collector.getAmount() + (amount*0.95f));
        collector.getTransactions().add(transaction);
        userRepository.save(collector);

        log.info("Receveur récupéré et mis à jour : " + collector.getFullName());
    }

    @Override
    public void bankToUser(String emailUser, float amount) {
        log.info("Service BankToUser:");

        String dateNow = LocalDate.now().toString();

        User collector = userRepository.findByEmail(emailUser);

        Transaction transaction = Transaction
                .builder()
                .typeOfTransaction(TypeOfTransaction.BankToUser)
                .amount(amount)
                .date(dateNow)
                .description("Compte Bancaire de " + emailUser + " vers compte PMB.")
                .collector(collector)
                .build();

        log.info("Transaction générée : " + transaction);

        BankLog bankLog = BankLog
                .builder()
                .typeOfTransaction(TypeOfTransaction.BankToUser)
                .date(dateNow)
                .amount(amount)
                .description("Compte Bancaire de " + emailUser + " vers compte PMB.")
                .build();

        bankLogsRepository.save(bankLog);

        log.info("BankLog rédigé et envoyé :"+bankLog);

        collector.setAmount(collector.getAmount() + amount);
        collector.getTransactions().add(transaction);
        userRepository.save(collector);

        log.info("Receveur récupéré et mis à jour : " + collector.getFullName());

    }

    @Override
    public void userToBank(String emailUser, float amount) {
        log.info("Service UserToBank:");

        String dateNow = LocalDate.now().toString();

        User sender = userRepository.findByEmail(emailUser);
        log.info(""+sender);

        Transaction transaction = Transaction
                .builder()
                .typeOfTransaction(TypeOfTransaction.UserToBank)
                .amount(amount)
                .date(dateNow)
                .description("Compte PMB vers Compte Bancaire de " + emailUser + ".")
                .sender(sender)
                .build();

        log.info("Transaction générée : " + transaction);

        sender.setAmount(sender.getAmount() - amount);
        sender.getTransactions().add(transaction);
        userRepository.save(sender);

        log.info("Envoyeur récupéré et mis à jour : " + sender.getFullName());

        BankLog bankLog = BankLog
                .builder()
                .typeOfTransaction(TypeOfTransaction.UserToBank)
                .date(dateNow)
                .amount(amount)
                .description("Compte Bancaire de " + emailUser + " vers compte PMB.")
                .build();

        bankLogsRepository.save(bankLog);

        log.info("BankLog rédigé et envoyé :"+bankLog);


    }
}
