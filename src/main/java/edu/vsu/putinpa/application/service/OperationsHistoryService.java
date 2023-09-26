package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.JournalOperation;

import java.util.Collection;

public interface OperationsHistoryService {
    void add(Operation<?> operation);
    Collection<JournalOperation> getAll();
    Collection<JournalOperation> getTransfers();
    Collection<JournalOperation> getTransfers(Account account);
    Collection<JournalOperation> getIncomingTransfers();
    Collection<JournalOperation> getIncomingTransfers(Account account);
    Collection<JournalOperation> getOutgoingTransfers();
    Collection<JournalOperation> getOutgoingTransfers(Account account);
}
