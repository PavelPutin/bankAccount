package edu.vsu.putinpa.application.repository.impl;

import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.repository.OperationsRepository;

import java.util.*;
import java.util.stream.Collectors;

//@Component
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
    public List<JournalOperation> findByClient(Client client) {
        return data.values().stream()
                .filter(journalOperation -> journalOperation.getClient().equals(client))
                .collect(Collectors.toList());
    }

    @Override
    public List<JournalOperation> findAll() {
        return List.copyOf(data.values());
    }
}
