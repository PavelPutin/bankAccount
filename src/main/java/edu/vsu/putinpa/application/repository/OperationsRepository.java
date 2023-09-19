package edu.vsu.putinpa.application.repository;

import edu.vsu.putinpa.application.model.operations.OperationImpl;
import edu.vsu.putinpa.application.model.operations.OperationType;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface OperationsRepository {
    Optional<OperationImpl> getByUUID(UUID uuid);
    Collection<OperationImpl> getByType(OperationType type);
}
