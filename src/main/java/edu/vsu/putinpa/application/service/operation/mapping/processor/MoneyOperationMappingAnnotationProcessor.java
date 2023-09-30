package edu.vsu.putinpa.application.service.operation.mapping.processor;

import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.operation.mapping.OperationMappingAnnotationProcessor;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.MoneyInfo;

import static edu.vsu.putinpa.application.service.operation.mapping.OperationMappingAnnotationProcessor.traverseFields;


public class MoneyOperationMappingAnnotationProcessor implements OperationMappingAnnotationProcessor {
    @Override
    public void insertValueIntoJournalOperation(Operation<?> operation, JournalOperation journalOperation) {
        Money money = (Money) traverseFields(operation.getInfo(), MoneyInfo.class);
        if (money == null) {
            money = (Money) traverseFields(operation, MoneyInfo.class);
        }
        journalOperation.setMoney(money);
    }
}
