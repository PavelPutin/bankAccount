package edu.vsu.putinpa.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Account {
    private final UUID uuid;
    private String name;
    private final LocalDateTime whenOpened;
    private LocalDateTime whenClosed;
    private final Client creator;
    private final List<Client> owners;

    {
        uuid = UUID.randomUUID();
        whenOpened = LocalDateTime.now();
        owners = new ArrayList<>();
    }

    public Account(String name, Client creator) {
        this.name = name;
        this.creator = creator;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(uuid, account.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
