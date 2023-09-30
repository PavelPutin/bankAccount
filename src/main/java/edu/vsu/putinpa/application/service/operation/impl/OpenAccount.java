package edu.vsu.putinpa.application.service.operation.impl;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.application.service.operation.info.OpeningAccountInfo;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.RecipientInfo;

import java.time.Instant;

public class OpenAccount extends Operation<OpeningAccountInfo> {
    public OpenAccount(OperationsService service, OpeningAccountInfo info) {
        super(service, info);
    }

    @Override
    public void execute() {
        Account created = new Account(
                getInfo().getName(),
                getInfo().getCurrency(),
                getInfo().getInvoker()
        );
        if (getInfo().getSender() != null && getInfo().getMoney() != null) {
            created.replenishment(getInfo().getMoney());
            getInfo().getSender().withdrawal(getInfo().getMoney());
        }

        getInfo().setCreated(created);

        getService().getAccountsService().save(created);
    }
}
