package edu.vsu.putinpa.application.service.operation.info;

import edu.vsu.putinpa.application.model.Client;

abstract public class OperationInfo {
    private Client invoker;

    public OperationInfo(Client invoker) {
        this.invoker = invoker;
    }

    public Client getInvoker() {
        return invoker;
    }
}
