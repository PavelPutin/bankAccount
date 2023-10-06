package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.repository.OperationsRepository;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsHistoryService;
import edu.vsu.putinpa.application.service.operation.mapping.OperationMappingAnnotationProcessor;
import edu.vsu.putinpa.application.service.operation.mapping.processor.MoneyOperationMappingAnnotationProcessor;
import edu.vsu.putinpa.application.service.operation.mapping.processor.RecipientOperationMappingAnnotationProcessor;
import edu.vsu.putinpa.application.service.operation.mapping.processor.SenderOperationMappingAnnotationProcessor;
import edu.vsu.putinpa.infrastructure.di.api.AutoInjected;
import edu.vsu.putinpa.infrastructure.di.api.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class OperationsHistoryServiceImpl implements OperationsHistoryService {
    private OperationsRepository operationsRepository;
    private List<OperationMappingAnnotationProcessor> mappingAnnotationProcessorList = new ArrayList<>();

    @AutoInjected
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
    public List<JournalOperation> getAll() {
        return operationsRepository.findAll();
    }

    @Override
    public List<JournalOperation> getAllByClient(Client client) {
        return operationsRepository.findByClient(client);
    }

    @Override
    public List<JournalOperation> getAllByClientAndAccount(Client client, Account account) {
        return operationsRepository.findByClientAndAccount(client, account);
    }
}
