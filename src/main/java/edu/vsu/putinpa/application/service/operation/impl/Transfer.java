package edu.vsu.putinpa.application.service.operation.impl;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.application.service.operation.info.TransferInfo;

import java.util.List;

public class Transfer extends Operation<TransferInfo> {
    public Transfer(OperationsService service, TransferInfo info) {
        super(service, info);
    }

    @Override
    public void execute() {
        if (!getInfo().getSender().getCreator().equals(getInfo().getInvoker())) {
            throw new IllegalStateException("%s tried to transfer from account %s he didn't create."
                    .formatted(getInfo().getInvoker().getUuid(), getInfo().getSender().getUuid()));
        }

        getInfo().getSender().withdrawal(getInfo().getMoney());
        getInfo().getRecipient().replenishment(getInfo().getMoney());

        for (Account a : List.of(getInfo().getSender(), getInfo().getRecipient()))
            getService().getAccountsService().save(a);
    }
}
