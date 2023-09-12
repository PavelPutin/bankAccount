package edu.vsu.putinpa.repository;

import edu.vsu.putinpa.model.operations.Operation;
import edu.vsu.putinpa.model.operations.OperationType;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface OperationsRepository {
    Optional<Operation> getByUUID(UUID uuid);
    Collection<Operation> getByType(OperationType type);
}
