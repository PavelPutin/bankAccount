package edu.vsu.putinpa.model.operations.transfer;

import edu.vsu.putinpa.model.Account;
import edu.vsu.putinpa.model.Money;

public class Incoming extends Transfer {

    public Incoming(Account sender, Account recipient, Money value, String description) {
        super(sender, recipient, value, description);
    }
}
