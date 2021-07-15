package fr.orionbs.PayMyBuddy.service.impl;

import fr.orionbs.PayMyBuddy.model.BankLog;
import fr.orionbs.PayMyBuddy.repository.BankLogsRepository;
import fr.orionbs.PayMyBuddy.service.BankLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class BankLogsServiceImpl implements BankLogsService {

    @Autowired
    BankLogsRepository bankLogsRepository;

    @Override
    public BankLog addBankLog(BankLog bankLog) {
        return bankLogsRepository.save(bankLog);
    }
}
