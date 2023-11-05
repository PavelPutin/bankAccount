package edu.vsu.putinpa.application.service;

import edu.vsu.putinpa.application.model.OperationType;
import edu.vsu.putinpa.application.service.operation.info.OperationInfo;

abstract public class Operation <T extends OperationInfo> {
    private OperationsService service;
    private T info;

    public Operation(OperationsService service, T info) {
        this.service = service;
        this.info = info;
    }

    public OperationsService getService() {
        return service;
    }

    public T getInfo() {
        return info;
    }

    public abstract OperationType getType();

    abstract public void execute();
}
