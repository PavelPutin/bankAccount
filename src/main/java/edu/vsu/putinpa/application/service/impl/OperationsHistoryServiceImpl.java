package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.model.Account;
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

    @Override
    public Collection<JournalOperation> getTransfers() {
        return null;
    }

    @Override
    public Collection<JournalOperation> getTransfers(Account account) {
        return null;
    }

    @Override
    public Collection<JournalOperation> getIncomingTransfers() {
        return null;
    }

    @Override
    public Collection<JournalOperation> getIncomingTransfers(Account account) {
        return null;
    }

    @Override
    public Collection<JournalOperation> getOutgoingTransfers() {
        return null;
    }

    @Override
    public Collection<JournalOperation> getOutgoingTransfers(Account account) {
        return null;
    }
}
