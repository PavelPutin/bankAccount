package edu.vsu.putinpa.model.operations.transfer;

import edu.vsu.putinpa.model.Account;
import edu.vsu.putinpa.model.Money;

public class Outgoing extends Transfer {

    public Outgoing(Account sender, Account recipient, Money value, String description) {
        super(sender, recipient, value, description);
    }
}
