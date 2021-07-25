package fr.orionbs.PayMyBuddy.service;

public interface TransactionService {
    public void bankToUser(String emailUser, float amount);
    public void userToBank(String emailUser, float amount);
    public void userToUser(String emailUserSender, String emailUserCollector, float amount, String description);
}
