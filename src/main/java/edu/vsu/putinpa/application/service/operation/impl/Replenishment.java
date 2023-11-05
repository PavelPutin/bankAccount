package edu.vsu.putinpa.application.service.operation.impl;

import edu.vsu.putinpa.application.model.OperationType;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsService;
import edu.vsu.putinpa.application.service.operation.info.ReplenishmentInfo;

public class Replenishment extends Operation<ReplenishmentInfo> {
    public Replenishment(OperationsService service, ReplenishmentInfo info) {
        super(service, info);
    }

    @Override
    public void execute() {
        if (!getInfo().getRecipient().getCreator().equals(getInfo().getInvoker())) {
            throw new IllegalStateException("%s tried to replenish account %s he didn't create."
                    .formatted(getInfo().getInvoker().getUuid(), getInfo().getRecipient().getUuid()));
        }

        getInfo().getRecipient().replenishment(getInfo().getMoney());
        getService().getAccountsService().save(getInfo().getRecipient());
    }

    @Override
    public OperationType getType() {
        return OperationType.REPLENISHMENT;
    }
}
