package edu.vsu.putinpa.application.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Client extends EntityWithUUID {
    private String name;
    private String password;
    private Instant whenCreated;
    private final List<Account> accounts;

    {
        whenCreated = Instant.now();
        accounts = new ArrayList<>();
    }

    public Client(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getWhenCreated() {
        return whenCreated;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setWhenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
    }
}
