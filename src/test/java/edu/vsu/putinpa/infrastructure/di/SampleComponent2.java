package edu.vsu.putinpa.infrastructure.di;

import edu.vsu.putinpa.infrastructure.di.api.AutoInjected;
import edu.vsu.putinpa.infrastructure.di.api.Component;

@Component(name="comp2")
public class SampleComponent2 implements Sample2 {
    private final Sample1 sampleComponent1;

    @AutoInjected
    public SampleComponent2(Sample1 sampleComponent1) {
        this.sampleComponent1 = sampleComponent1;
    }

    public Sample1 getSampleComponent1() {
        return sampleComponent1;
    }
}
