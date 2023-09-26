package edu.vsu.putinpa.application.service.operation;

import edu.vsu.putinpa.application.dto.OperationInfoDto;
import edu.vsu.putinpa.application.dto.ReplenishmentInfoDto;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsService;

import java.time.Instant;

public class Replenishment extends Operation<ReplenishmentInfoDto> {
    public Replenishment(OperationsService service, ReplenishmentInfoDto info) {
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
