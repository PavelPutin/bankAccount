package edu.vsu.putinpa.application.repository.impl;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.repository.OperationsRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryOperationRepository implements OperationsRepository {
    private final Map<UUID, JournalOperation> data = new HashMap<>();

    public InMemoryOperationRepository() {}

    public InMemoryOperationRepository(JournalOperation... journalOperations) {
        for (JournalOperation journalOperation : journalOperations) {
            data.put(journalOperation.getUuid(), journalOperation);
        }
    }

    @Override
    public JournalOperation save(JournalOperation journalOperation) {
        return data.put(journalOperation.getUuid(), journalOperation);
    }

    @Override
    public Optional<JournalOperation> getByUUID(UUID uuid) {
        return Optional.ofNullable(data.get(uuid));
    }

    @Override
    public Collection<JournalOperation> getByClient(Client client) {
        return data.values().stream()
                .filter(journalOperation -> journalOperation.getClient().equals(client))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<JournalOperation> getBySender(Account account) {
        return data.values().stream()
                .filter(journalOperation -> journalOperation.getSender().equals(account))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<JournalOperation> getByRecipient(Account recipient) {
        return data.values().stream()
                .filter(journalOperation -> journalOperation.getRecipient().equals(recipient))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<JournalOperation> getBySenderOrRecipient(Account account) {
        return data.values().stream()
                .filter(journalOperation -> journalOperation.getRecipient().equals(account) || journalOperation.getSender().equals(account))
                .collect(Collectors.toList());
    }
}
