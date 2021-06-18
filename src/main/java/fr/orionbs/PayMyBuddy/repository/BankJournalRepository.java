package fr.orionbs.PayMyBuddy.repository;

import fr.orionbs.PayMyBuddy.model.BankJournal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankJournalRepository extends JpaRepository<BankJournal, Integer> {
}
