package edu.vsu.putinpa.application.repository;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.JournalOperation;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface OperationsRepository {
    Optional<JournalOperation> findByUUID(UUID uuid);
    Collection<JournalOperation> findByClient(Client client);
    Collection<JournalOperation> findByClientAndAccount(Client client, Account account);
    Collection<JournalOperation> findAll();
    JournalOperation save(JournalOperation journalOperation);
}
