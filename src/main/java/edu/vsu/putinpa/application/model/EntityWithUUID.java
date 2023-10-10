package edu.vsu.putinpa.application.model;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

abstract public class EntityWithUUID {
    private static Supplier<UUID> defaultSupplier = UUID::randomUUID;
    private final UUID uuid;

    public EntityWithUUID() {
        uuid = defaultSupplier.get();
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

    public static void setDefaultUUIDSupplier(Supplier<UUID> uuidSupplier) {
        defaultSupplier = uuidSupplier;
    }
}
