package edu.vsu.putinpa.application.repository;

import edu.vsu.putinpa.application.service.operations.OperationImpl;
import edu.vsu.putinpa.application.service.operations.OperationType;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface OperationsRepository {
    Optional<OperationImpl> getByUUID(UUID uuid);
}
