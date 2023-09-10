package edu.vsu.putinpa.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Account extends EntityWithUUID {
    private String name;
    private final LocalDateTime whenOpened;
    private LocalDateTime whenClosed;
    private final Client creator;
    private final List<Client> owners;

    {
        whenOpened = LocalDateTime.now();
        owners = new ArrayList<>();
    }

    public Account(String name, Client creator) {
        this.name = name;
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getWhenOpened() {
        return whenOpened;
    }

    public LocalDateTime getWhenClosed() {
        return whenClosed;
    }

    public void setWhenClosed(LocalDateTime whenClosed) {
        this.whenClosed = whenClosed;
    }

    public Client getCreator() {
        return creator;
    }

    public List<Client> getOwners() {
        return List.copyOf(owners);
    }

    public void addOwner(Client owner) {
        owners.add(owner);
    }
}
