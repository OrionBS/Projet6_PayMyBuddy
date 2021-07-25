package fr.orionbs.PayMyBuddy.serviceTest;

import fr.orionbs.PayMyBuddy.model.BankLog;
import fr.orionbs.PayMyBuddy.model.TypeOfTransaction;
import fr.orionbs.PayMyBuddy.model.User;
import fr.orionbs.PayMyBuddy.repository.BankLogsRepository;
import fr.orionbs.PayMyBuddy.repository.UserRepository;
import fr.orionbs.PayMyBuddy.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    UserRepository userRepository;
    @Mock
    BankLogsRepository bankLogsRepository;
    @Mock
    User user;
    @Mock
    List list;

    @Test
    public void testUserToUser() {
        //GIVEN
        User sender = User.builder().email("sender@email.com").transactions(new ArrayList<>()).build();
        User collector = User.builder().email("collector@email.com").transactions(new ArrayList<>()).build();
        BankLog bankLog = BankLog.builder().typeOfTransaction(TypeOfTransaction.Commission).amount(10f).date(LocalDate.now().toString()).description("Commission sur transaction entre null et null.").build();

        when(userRepository.save(any(User.class))).thenReturn(User.builder().build());

        when(userRepository.findByEmail(sender.getEmail())).thenReturn(sender);
        when(userRepository.findByEmail(collector.getEmail())).thenReturn(collector);

        when(bankLogsRepository.save(any(BankLog.class))).thenReturn(any(BankLog.class));

        //WHEN
        transactionService.userToUser(sender.getEmail(),collector.getEmail(),100,"test");

        //THEN
        verify(userRepository, Mockito.times(2)).findByEmail(anyString());
        verify(bankLogsRepository, Mockito.times(1)).save(any(BankLog.class));
        verify(userRepository, Mockito.times(2)).save(any(User.class));


    }

    @Test
    public void testUserToBank() {
        //GIVEN
        User sender = User.builder().id(1).email("test@email.com").password("123456").firstName("test").lastName("Test").amount(1000f).transactions(new ArrayList<>()).build();
        BankLog bankLog = BankLog.builder().typeOfTransaction(TypeOfTransaction.Commission).amount(10f).date(LocalDate.now().toString()).description("Commission sur transaction entre null et null.").build();
        when(userRepository.findByEmail(sender.getEmail())).thenReturn(sender);
        when(userRepository.save(sender)).thenReturn(sender);
        when(bankLogsRepository.save(any(BankLog.class))).thenReturn(any(BankLog.class));

        //WHEN
        transactionService.userToBank(sender.getEmail(),100);

        //THEN
        verify(userRepository, Mockito.times(1)).findByEmail(anyString());
        verify(userRepository, Mockito.times(1)).save(any(User.class));
        verify(bankLogsRepository, Mockito.times(1)).save(any(BankLog.class));

    }

    @Test
    public void testBankToUser() {
        //GIVEN
        User collector = User.builder().id(1).email("test@email.com").password("123456").firstName("test").lastName("Test").amount(1000f).transactions(new ArrayList<>()).build();
        BankLog bankLog = BankLog.builder().typeOfTransaction(TypeOfTransaction.Commission).amount(10f).date(LocalDate.now().toString()).description("Commission sur transaction entre null et null.").build();
        when(userRepository.findByEmail(collector.getEmail())).thenReturn(collector);
        when(userRepository.save(collector)).thenReturn(collector);
        when(bankLogsRepository.save(any(BankLog.class))).thenReturn(any(BankLog.class));


        //WHEN
        transactionService.bankToUser(collector.getEmail(),100);

        //THEN
        verify(userRepository, Mockito.times(1)).findByEmail(anyString());
        verify(bankLogsRepository, Mockito.times(1)).save(any(BankLog.class));
        verify(userRepository, Mockito.times(1)).save(any(User.class));

    }
}
