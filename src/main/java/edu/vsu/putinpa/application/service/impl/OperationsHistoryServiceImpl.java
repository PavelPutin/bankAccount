package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.repository.OperationsRepository;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsHistoryService;

public class OperationsHistoryServiceImpl implements OperationsHistoryService {
    private OperationsRepository operationsRepository;

    public OperationsHistoryServiceImpl(OperationsRepository operationsRepository) {
        this.operationsRepository = operationsRepository;
    }

    @Override
    public void add(Operation<?> operation) {
        operationsRepository.save(operation.log());
    }
}
