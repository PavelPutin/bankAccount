package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.JournalOperation;

import java.util.List;

public interface OperationsHistoryService {
    void add(Operation<?> operation);
    List<JournalOperation> getAll();
    List<JournalOperation> getAllByClient(Client client);
}
