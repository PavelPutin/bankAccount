package edu.vsu.putinpa.infrastructure.di;

import edu.vsu.putinpa.infrastructure.di.api.AutoInjected;
import edu.vsu.putinpa.infrastructure.di.api.Component;

@Component(name="comp3")
public class SampleComponent3 {
    @AutoInjected
    private SampleComponent1 comp1;
    @AutoInjected
    private SampleComponent2 comp2;

    public SampleComponent1 getComp1() {
        return comp1;
    }

    public SampleComponent2 getComp2() {
        return comp2;
    }
}
