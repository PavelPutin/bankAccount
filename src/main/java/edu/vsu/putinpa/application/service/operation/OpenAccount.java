package edu.vsu.putinpa.application.service.operation;

import edu.vsu.putinpa.application.dto.OpeningAccountInfoDto;
import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsService;

import java.time.Instant;

public class OpenAccount extends Operation<OpeningAccountInfoDto> {
    private Account created;
    public OpenAccount(OperationsService service, OpeningAccountInfoDto info) {
        super(service, info);
    }

    @Override
    public void execute() {
        created = new Account(
                getInfo().getName(),
                getInfo().getCurrency(),
                getInfo().getInvoker()
        );
        if (getInfo().getSender() != null && getInfo().getMoney() != null) {
            created.replenishment(getInfo().getMoney());
            getInfo().getSender().withdrawal(getInfo().getMoney());
        }
        getService().getAccountsService().save(created);
    }

    @Override
    public JournalOperation log() {
        JournalOperation log = new JournalOperation(Instant.now(), getInfo().getInvoker());
        log.setSender(getInfo().getSender());
        log.setRecipient(created);
        log.setMoney(getInfo().getMoney());
        return log;
    }
}
