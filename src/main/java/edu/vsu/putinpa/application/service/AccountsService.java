package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;

public interface AccountsService {
    Account open(String name, Client creator, Money initialValue);
    Account close(Account account, Account recipient);
}
