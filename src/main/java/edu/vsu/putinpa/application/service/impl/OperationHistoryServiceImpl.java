package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.repository.OperationsRepository;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationHistoryService;

public class OperationHistoryServiceImpl implements OperationHistoryService {
    private OperationsRepository operationsRepository;

    public OperationHistoryServiceImpl(OperationsRepository operationsRepository) {
        this.operationsRepository = operationsRepository;
    }

    @Override
    public void add(Operation<?> operation) {
        operationsRepository.save(operation.log());
    }

    @Override
    public OperationsRepository getOperationsRepository() {
        return operationsRepository;
    }
}
