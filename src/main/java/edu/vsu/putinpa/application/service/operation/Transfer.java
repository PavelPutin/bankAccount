package edu.vsu.putinpa.application.service.operation;

import edu.vsu.putinpa.application.dto.TransferInfoDto;
import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.OperationsService;

import java.time.Instant;
import java.util.List;

public class Transfer extends Operation<TransferInfoDto> {
    public Transfer(OperationsService service, TransferInfoDto info) {
        super(service, info);
    }

    @Override
    public void execute() {
        getInfo().getSender().withdrawal(getInfo().getMoney());
        getInfo().getRecipient().replenishment(getInfo().getMoney());

        for (Account a : List.of(getInfo().getSender(), getInfo().getRecipient()))
            getService().getAccountsService().save(a);
    }

    @Override
    public JournalOperation log() {
        JournalOperation log = new JournalOperation(Instant.now(), getInfo().getInvoker());
        log.setSender(getInfo().getSender());
        log.setRecipient(getInfo().getRecipient());
        log.setMoney(getInfo().getMoney());
        return log;
    }
}
