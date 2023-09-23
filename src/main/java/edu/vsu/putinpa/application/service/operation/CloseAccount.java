package edu.vsu.putinpa.application.service.operation;

import edu.vsu.putinpa.application.dto.ClosingAccountInfoDto;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationService;

import java.time.Instant;

public class CloseAccount extends Operation<ClosingAccountInfoDto> {
    private Money balance;
    public CloseAccount(OperationService service, ClosingAccountInfoDto info) {
        super(service, info);
    }

    @Override
    public void execute() {
        getInfo().getTarget().setWhenClosed(Instant.now());
        balance = getInfo().getTarget().getBalance();
        getInfo().getRecipient().add(balance);
        getInfo().getTarget().subtract(balance);
    }

    @Override
    public JournalOperation log() {
        JournalOperation log = new JournalOperation(Instant.now(), getInfo().getInvoker());
        log.setSender(getInfo().getTarget());
        log.setRecipient(getInfo().getRecipient());
        log.setMoney(balance);
        return log;
    }
}
