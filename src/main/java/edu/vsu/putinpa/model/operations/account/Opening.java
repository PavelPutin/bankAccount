package edu.vsu.putinpa.model.operations.account;

import edu.vsu.putinpa.model.Account;
import edu.vsu.putinpa.model.Money;

final public class Opening extends OperationWithAccount {

    public Opening(Account account, Money value) {
        super(account, value);
    }
}
