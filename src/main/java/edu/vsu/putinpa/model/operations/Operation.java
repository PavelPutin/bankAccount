package edu.vsu.putinpa.model.operations;

import edu.vsu.putinpa.model.EntityWithUUID;

import java.time.LocalDateTime;
import java.util.Objects;

abstract public class Operation extends EntityWithUUID {
    private final LocalDateTime timestamp;

    {
        timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
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
