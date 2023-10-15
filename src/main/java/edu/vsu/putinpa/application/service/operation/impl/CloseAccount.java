package edu.vsu.putinpa.application.service.operation.impl;

import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.application.service.operation.info.ClosingAccountInfo;

import java.time.Instant;

public class CloseAccount extends Operation<ClosingAccountInfo> {
    public CloseAccount(OperationsService service, ClosingAccountInfo info) {
        super(service, info);
    }

    @Override
    public void execute() {
        if (!getInfo().getTarget().getCreator().equals(getInfo().getInvoker())) {
            throw new IllegalStateException("%s tried to close account %s he didn't create."
                    .formatted(getInfo().getInvoker().getUuid(), getInfo().getTarget().getUuid()));
        }

        if (!getInfo().getRecipient().getCreator().equals(getInfo().getInvoker())) {
            throw new IllegalStateException("%s tried to transfer money after closing to account %s he didn't create."
                    .formatted(getInfo().getInvoker().getUuid(), getInfo().getRecipient().getUuid()));
        }

        getInfo().getTarget().setWhenClosed(Instant.now());
        Money balance = getInfo().getTarget().getBalance();
        getInfo().getRecipient().replenishment(balance);
        getInfo().getTarget().withdrawal(balance);

        getInfo().setMoney(balance);

        getService().getAccountsService().save(getInfo().getTarget());
        getService().getAccountsService().save(getInfo().getRecipient());
    }
}
