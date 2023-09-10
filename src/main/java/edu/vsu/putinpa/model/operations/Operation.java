package edu.vsu.putinpa.model.operations;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

abstract public class Operation {
    private final UUID uuid;
    private final LocalDateTime timestamp;

    {
        uuid = UUID.randomUUID();
        timestamp = LocalDateTime.now();
    }

    public UUID getUuid() {
        return uuid;
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
