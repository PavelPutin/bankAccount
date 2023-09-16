package edu.vsu.putinpa.infrastructure.di;

import java.util.HashMap;
import java.util.Set;

public class AnnotationContext {
    private final HashMap<String, Object> singletonHash = new HashMap<>();

    {
        singletonHash.put("c1", 5);
        singletonHash.put("c2", "abc");
    }

    public Set<String> componentNames() {
        return singletonHash.keySet();
    }

    public <T> T getComponent(String name, Class<T> requiredType) {
        Object component = singletonHash.get(name);
        if (component != null) {
            try {
                return (T) component;
            } catch (ClassCastException e) {
                throw new RuntimeException("Incompatible class type for component with name %s".formatted(name), e);
            }
        }
        throw new RuntimeException("No component with name %s".formatted(name));
    }
}
