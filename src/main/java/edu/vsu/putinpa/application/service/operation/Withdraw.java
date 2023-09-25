package edu.vsu.putinpa.application.service.operation;

import edu.vsu.putinpa.application.dto.WithdrawalInfoDto;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsService;

import java.time.Instant;

public class Withdraw extends Operation<WithdrawalInfoDto> {
    public Withdraw(OperationsService service, WithdrawalInfoDto info) {
        super(service, info);
    }

    @Override
    public void execute() {
        getInfo().getTarget().withdrawal(getInfo().getMoney());
    }

    @Override
    public JournalOperation log() {
        JournalOperation log = new JournalOperation(Instant.now(), getInfo().getInvoker());
        log.setSender(getInfo().getTarget());
        log.setMoney(getInfo().getMoney());
        return log;
    }
}
