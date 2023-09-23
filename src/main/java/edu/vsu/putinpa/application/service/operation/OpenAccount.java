package edu.vsu.putinpa.application.service.operation;

import edu.vsu.putinpa.application.dto.OpeningAccountInfoDto;
import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationService;

import java.time.Instant;

public class OpenAccount extends Operation<OpeningAccountInfoDto> {
    private Account created;
    public OpenAccount(OperationService service, OpeningAccountInfoDto info) {
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
            created.add(getInfo().getMoney());
            getInfo().getSender().subtract(getInfo().getMoney());
        }
        getService().getAccountRepository().save(created);
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
