package edu.vsu.putinpa.application.model.operations.transfer;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.model.operations.OperationImpl;

 public class Transfer extends OperationImpl {
    private final Account sender;
    private final Account recipient;

    public Transfer(Account sender, Account recipient, Money money) {
        super(money);
        this.sender = sender;
        this.recipient = recipient;
    }

    public Account getSender() {
        return sender;
    }

    public Account getRecipient() {
        return recipient;
    }

    @Override
    public void execute() {
        sender.getBalance().subtract(getMoney());
        recipient.getBalance().add(getMoney());
    }
}
