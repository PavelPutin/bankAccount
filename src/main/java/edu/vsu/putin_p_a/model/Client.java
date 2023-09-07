package edu.vsu.putin_p_a.model;

import edu.vsu.putin_p_a.model.operations.Operation;

import java.util.List;
import java.util.UUID;

public class Client {
    UUID uuid;
    String name;
    List<Account> accounts;
    List<Operation> operationsHistory;
}
