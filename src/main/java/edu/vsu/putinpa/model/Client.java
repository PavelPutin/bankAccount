package edu.vsu.putin_p_a.model;

import edu.vsu.putin_p_a.model.operations.Operation;

import java.util.List;
import java.util.UUID;

public class Client {
    private UUID uuid;
    private String name;
    private List<Account> accounts;
    private List<Operation> operationsHistory;
}
