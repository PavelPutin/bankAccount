package edu.vsu.putinpa.infrastructure.di;

import edu.vsu.putinpa.infrastructure.di.api.Component;

@Component
public class SampleComponent1 implements Sample1{
    private String name = "component 1";

    public String getName() {
        return name;
    }
}
