package fr.orionbs.PayMyBuddy.serviceTest;

import fr.orionbs.PayMyBuddy.model.BankLog;
import fr.orionbs.PayMyBuddy.repository.BankLogsRepository;
import fr.orionbs.PayMyBuddy.service.impl.BankLogsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankLogsService {

    @InjectMocks
    BankLogsServiceImpl bankLogsService;

    @Mock
    BankLogsRepository bankLogsRepository;

    @Test
    public void testAddBankLog() {
        //GIVEN
        when(bankLogsRepository.save(any(BankLog.class))).thenReturn(new BankLog());

        //WHEN
        bankLogsService.addBankLog(new BankLog());

        //THEN
        verify(bankLogsRepository, Mockito.times(1)).save(any(BankLog.class));
    }
}
