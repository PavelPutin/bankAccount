package edu.vsu.putinpa.application.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Client extends EntityWithUUID {
    private String name;
    private final LocalDateTime whenCreated;
    private final List<Account> accounts;

    {
        whenCreated = LocalDateTime.now();
        accounts = new ArrayList<>();
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
}
