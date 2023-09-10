package edu.vsu.putinpa.model;

import edu.vsu.putinpa.model.operations.Operation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Client extends EntityWithUUID {
    private String name;
    private final LocalDateTime whenCreated;
    private final List<Account> accounts;
    private final List<Operation> operationsHistory;

    {
        whenCreated = LocalDateTime.now();
        accounts = new ArrayList<>();
        operationsHistory = new ArrayList<>();
    }

    public Client(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getWhenCreated() {
        return whenCreated;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Operation> getOperationsHistory() {
        return operationsHistory;
    }
}
