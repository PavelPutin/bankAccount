package edu.vsu.putin_p_a.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Account {
    UUID uuid;
    String name;
    LocalDateTime whenOpened;
    LocalDateTime whenClosed;
    Client creator;
    List<Client> owners;
}
