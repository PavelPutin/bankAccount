package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.repository.OperationsRepository;

public interface OperationsHistoryService {
    void add(Operation<?> operation);
}
