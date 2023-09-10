package edu.vsu.putinpa.model;

import edu.vsu.putinpa.model.operations.Operation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Client {
    private final UUID uuid;
    private String name;
    private final LocalDateTime whenCreated;
    private final List<Account> accounts;
    private final List<Operation> operationsHistory;

    {
        uuid = UUID.randomUUID();
        whenCreated = LocalDateTime.now();
        accounts = new ArrayList<>();
        operationsHistory = new ArrayList<>();
    }

    public Client(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(getUuid(), client.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }
}
