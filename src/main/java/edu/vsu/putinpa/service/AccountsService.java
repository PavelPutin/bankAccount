package edu.vsu.putinpa.service;

import edu.vsu.putinpa.model.Account;
import edu.vsu.putinpa.model.Client;
import edu.vsu.putinpa.model.Money;

public interface AccountsService {
    Account open(String name, Client creator, Money initialValue);
    Account close(Account account);
}
