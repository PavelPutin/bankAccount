package edu.vsu.putinpa.infrastructure.di;

import edu.vsu.putinpa.infrastructure.di.api.Autowire;
import edu.vsu.putinpa.infrastructure.di.api.Component;

@Component(name="comp2")
public class SampleComponent2 {
    @Autowire
    private final SampleComponent1 sampleComponent1 = null;

    public SampleComponent1 getSampleComponent1() {
        return sampleComponent1;
    }
}
