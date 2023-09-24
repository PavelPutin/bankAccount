package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.repository.OperationsRepository;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsHistoryService;

import java.util.Collection;

public class OperationsHistoryServiceImpl implements OperationsHistoryService {
    private OperationsRepository operationsRepository;

    public OperationsHistoryServiceImpl(OperationsRepository operationsRepository) {
        this.operationsRepository = operationsRepository;
    }

    @Override
    public void add(Operation<?> operation) {
        operationsRepository.save(operation.log());
    }

    @Override
    public Collection<JournalOperation> getAll() {
        return operationsRepository.findAll();
    }
}
