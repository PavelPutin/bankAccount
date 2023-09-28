package edu.vsu.putinpa.application.service.operation.mapping.processor;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.operation.mapping.OperationMappingAnnotationProcessor;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.SenderInfo;

import static edu.vsu.putinpa.application.service.operation.mapping.OperationMappingAnnotationProcessor.traverseFields;

public class SenderOperationMappingAnnotationProcessor implements OperationMappingAnnotationProcessor {
    @Override
    public void insertValueIntoJournalOperation(Operation<?> operation, JournalOperation journalOperation) {
        Account sender = (Account) traverseFields(operation, SenderInfo.class);
        journalOperation.setSender(sender);
    }
}
