package fr.orionbs.PayMyBuddy.service;

public interface TransactionService {
    public void BankToUser(String emailUser,float amount);
    public void UserToBank(String emailUser,float amount);
    public void UserToUser(String emailUserSender, String emailUserCollector, float amount);
}
