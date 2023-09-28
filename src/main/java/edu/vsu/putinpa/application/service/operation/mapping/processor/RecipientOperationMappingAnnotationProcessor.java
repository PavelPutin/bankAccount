package edu.vsu.putinpa.application.service.operation.mapping.processor;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.operation.mapping.OperationMappingAnnotationProcessor;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.RecipientInfo;

import static edu.vsu.putinpa.application.service.operation.mapping.OperationMappingAnnotationProcessor.traverseFields;


public class RecipientOperationMappingAnnotationProcessor implements OperationMappingAnnotationProcessor {
    @Override
    public void insertValueIntoJournalOperation(Operation<?> operation, JournalOperation journalOperation) {
        Account recipient = (Account) traverseFields(operation.getInfo(), RecipientInfo.class);
        if (recipient == null) {
            recipient = (Account) traverseFields(operation, RecipientInfo.class);
        }
        journalOperation.setRecipient(recipient);
    }
}
