package edu.vsu.putinpa.application.service.operation.impl;

import edu.vsu.putinpa.application.model.OperationType;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.application.service.operation.info.WithdrawalInfo;

public class Withdraw extends Operation<WithdrawalInfo> {
    public Withdraw(OperationsService service, WithdrawalInfo info) {
        super(service, info);
    }

    @Override
    public void execute() {
        if (!getInfo().getSender().getCreator().equals(getInfo().getInvoker())) {
            throw new IllegalStateException("%s tried to withdraw from account %s he didn't create."
                    .formatted(getInfo().getInvoker().getUuid(), getInfo().getSender().getUuid()));
        }

        getInfo().getSender().withdrawal(getInfo().getMoney());
        getService().getAccountsService().save(getInfo().getSender());
    }

    @Override
    public OperationType getType() {
        return OperationType.WITHDRAW;
    }
}
