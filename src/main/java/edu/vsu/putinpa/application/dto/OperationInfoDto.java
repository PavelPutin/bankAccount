package edu.vsu.putinpa.application.dto;

import edu.vsu.putinpa.application.model.Client;

abstract public class OperationInfoDto {
    private Client invoker;

    public OperationInfoDto(Client invoker) {
        this.invoker = invoker;
    }

    public Client getInvoker() {
        return invoker;
    }
}
