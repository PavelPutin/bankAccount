package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.dto.OperationInfoDto;
import edu.vsu.putinpa.application.model.JournalOperation;

abstract public class Operation <T extends OperationInfoDto> {
    private OperationService service;
    private T info;

    public Operation(OperationService service, T info) {
        this.service = service;
        this.info = info;
    }

    public OperationService getService() {
        return service;
    }

    public T getInfo() {
        return info;
    }

    abstract public void execute();
    abstract public JournalOperation log();
}
