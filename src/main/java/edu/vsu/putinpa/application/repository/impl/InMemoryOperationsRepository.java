package edu.vsu.putinpa.application.repository.impl;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.repository.OperationsRepository;
import edu.vsu.putinpa.infrastructure.di.api.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryOperationsRepository implements OperationsRepository {
    private final Map<UUID, JournalOperation> data = new HashMap<>();

    public InMemoryOperationsRepository() {}

    public InMemoryOperationsRepository(JournalOperation... journalOperations) {
        for (JournalOperation journalOperation : journalOperations) {
            data.put(journalOperation.getUuid(), journalOperation);
        }
    }

    @Override
    public JournalOperation save(JournalOperation journalOperation) {
        return data.put(journalOperation.getUuid(), journalOperation);
    }

    @Override
    public Optional<JournalOperation> findByUUID(UUID uuid) {
        return Optional.ofNullable(data.get(uuid));
    }

    @Override
    public Collection<JournalOperation> findByClient(Client client) {
        return data.values().stream()
                .filter(journalOperation -> journalOperation.getClient().equals(client))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<JournalOperation> findBySender(Account account) {
        return data.values().stream()
                .filter(journalOperation -> journalOperation.getSender().equals(account))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<JournalOperation> findByRecipient(Account recipient) {
        return data.values().stream()
                .filter(journalOperation -> journalOperation.getRecipient().equals(recipient))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<JournalOperation> findBySenderOrRecipient(Account account) {
        return data.values().stream()
                .filter(journalOperation -> journalOperation.getRecipient().equals(account) || journalOperation.getSender().equals(account))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<JournalOperation> findAll() {
        return data.values();
    }
}
