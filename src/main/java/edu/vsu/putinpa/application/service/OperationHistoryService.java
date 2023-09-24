package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.repository.OperationsRepository;

import java.util.List;

public interface OperationHistoryService {
    void add(Operation<?> operation);
    OperationsRepository getOperationsRepository();
}
