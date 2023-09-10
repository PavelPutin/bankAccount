package edu.vsu.putin_p_a.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Account {
    private UUID uuid;
    private String name;
    private LocalDateTime whenOpened;
    private LocalDateTime whenClosed;
    private Client creator;
    private List<Client> owners;
}
