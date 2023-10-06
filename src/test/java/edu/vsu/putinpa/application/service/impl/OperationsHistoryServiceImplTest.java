package edu.vsu.putinpa.application.service.impl;

import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.repository.OperationsRepository;
import edu.vsu.putinpa.application.repository.impl.InMemoryOperationsRepository;
import edu.vsu.putinpa.application.service.OperationsHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OperationsHistoryServiceImplTest {
    private OperationsRepository repository;
    private OperationsHistoryService operationsHistory;

    @BeforeEach
    public void init() {
        repository = new InMemoryOperationsRepository();
        operationsHistory = new OperationsHistoryServiceImpl(repository);
    }

    @Test
    public void whenGetAll_thenReturnAllOperations() {
        List<JournalOperation> journalOperations = List.of(
                new JournalOperation(Instant.now(), new Client("test", "test")),
                new JournalOperation(Instant.now(), new Client("test1", "test"))
        );
        journalOperations.forEach(repository::save);
        assertEquals(journalOperations, operationsHistory.getAll());
    }

    @Test
    public void givenOperation_whenAdd_thenGetAllReturnOnlyIt() {

    }
}