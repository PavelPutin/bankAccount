package edu.vsu.putinpa.application.service.operation.mapping;

import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.service.Operation;

public interface OperationMappingAnnotationProcessor {
    void insertValueIntoJournalOperation(Operation<?> operation, JournalOperation journalOperation);
}
