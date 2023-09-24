package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.model.JournalOperation;

import java.util.Collection;

public interface OperationsHistoryService {
    void add(Operation<?> operation);
    Collection<JournalOperation> getAll();
}
