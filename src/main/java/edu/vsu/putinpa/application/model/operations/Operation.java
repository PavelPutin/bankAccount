package edu.vsu.putinpa.application.model.operations;

import edu.vsu.putinpa.application.model.EntityWithUUID;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

abstract public class Operation extends EntityWithUUID {
    private final LocalDateTime timestamp;
    private final Set<OperationType> types;

    {
        timestamp = LocalDateTime.now();
        types = initTypes();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Set<OperationType> getTypes() {
        return types;
    }

    protected Set<OperationType> initTypes() {
        return Collections.emptySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(getUuid(), operation.getUuid()) && Objects.equals(getTimestamp(), operation.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getTimestamp());
    }
}
