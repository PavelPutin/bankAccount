package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.repository.OperationsRepository;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsHistoryService;
import edu.vsu.putinpa.application.service.operation.mapping.OperationMappingAnnotationProcessor;
import edu.vsu.putinpa.application.service.operation.mapping.processor.MoneyOperationMappingAnnotationProcessor;
import edu.vsu.putinpa.application.service.operation.mapping.processor.RecipientOperationMappingAnnotationProcessor;
import edu.vsu.putinpa.application.service.operation.mapping.processor.SenderOperationMappingAnnotationProcessor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OperationsHistoryServiceImpl implements OperationsHistoryService {
    private OperationsRepository operationsRepository;
    private List<OperationMappingAnnotationProcessor> mappingAnnotationProcessorList = new ArrayList<>();

    public OperationsHistoryServiceImpl(OperationsRepository operationsRepository) {
        this.operationsRepository = operationsRepository;
        mappingAnnotationProcessorList.addAll(List.of(
                new RecipientOperationMappingAnnotationProcessor(),
                new SenderOperationMappingAnnotationProcessor(),
                new MoneyOperationMappingAnnotationProcessor()
        ));
    }

    @Override
    public void add(Operation<?> operation) {
        JournalOperation journalOperation = new JournalOperation(Instant.now(), operation.getInfo().getInvoker());
        for (var processor : mappingAnnotationProcessorList) {
            processor.insertValueIntoJournalOperation(operation, journalOperation);
        }
        operationsRepository.save(journalOperation);
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
