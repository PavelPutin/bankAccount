package edu.vsu.putinpa.application.service.operation.impl;

import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.application.service.operation.info.ReplenishmentInfo;

import java.time.Instant;

public class Replenishment extends Operation<ReplenishmentInfo> {
    public Replenishment(OperationsService service, ReplenishmentInfo info) {
        super(service, info);
    }

    @Override
    public void execute() {
        getInfo().getTarget().replenishment(getInfo().getMoney());
        getService().getAccountsService().save(getInfo().getTarget());
    }

    @Override
    public JournalOperation log() {
        JournalOperation log = new JournalOperation(Instant.now(), getInfo().getInvoker());
        log.setRecipient(getInfo().getTarget());
        log.setMoney(getInfo().getMoney());
        return log;
    }
}
