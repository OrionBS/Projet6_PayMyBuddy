package fr.orionbs.PayMyBuddy.repository;

import fr.orionbs.PayMyBuddy.model.BankLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankLogsRepository extends JpaRepository<BankLog, Integer> {

}
