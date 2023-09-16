package edu.vsu.putinpa.application.model;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

abstract public class EntityWithUUID {
    private final UUID uuid;

    public EntityWithUUID() {
        uuid = UUID.randomUUID();
    }

    public EntityWithUUID(Supplier<UUID> uuidSupplier) {
        uuid = uuidSupplier.get();
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityWithUUID that = (EntityWithUUID) o;
        return Objects.equals(getUuid(), that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }
}
