package edu.vsu.putinpa.application.service.operation.impl;

import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.application.service.operation.info.WithdrawalInfo;

import java.time.Instant;

public class Withdraw extends Operation<WithdrawalInfo> {
    public Withdraw(OperationsService service, WithdrawalInfo info) {
        super(service, info);
    }

    @Override
    public void execute() {
        getInfo().getTarget().withdrawal(getInfo().getMoney());
        getService().getAccountsService().save(getInfo().getTarget());
    }

    @Override
    public JournalOperation log() {
        JournalOperation log = new JournalOperation(Instant.now(), getInfo().getInvoker());
        log.setSender(getInfo().getTarget());
        log.setMoney(getInfo().getMoney());
        return log;
    }
}
