package edu.vsu.putinpa.application.service.operations;

import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.repository.AccountRepository;
import edu.vsu.putinpa.application.service.Operation;

abstract public class OperationImpl implements Operation {
    private AccountRepository repository;
    private final Money money;
    private String description;

    public OperationImpl(Money money) {
        this.money = money;
    }

    public Money getMoney() {
        return money;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public AccountRepository getRepository() {
        return repository;
    }

    public void setRepository(AccountRepository repository) {
        this.repository = repository;
    }
}
