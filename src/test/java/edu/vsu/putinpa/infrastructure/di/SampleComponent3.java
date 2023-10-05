package edu.vsu.putinpa.infrastructure.di;

import edu.vsu.putinpa.infrastructure.di.api.AutoInjected;
import edu.vsu.putinpa.infrastructure.di.api.Component;

@Component(name="comp3")
public class SampleComponent3 {
    private Sample1 comp1;
    private Sample2 comp2;

    @AutoInjected
    public SampleComponent3(Sample1 comp1, Sample2 comp2) {
        this.comp1 = comp1;
        this.comp2 = comp2;
    }

    public Sample1 getComp1() {
        return comp1;
    }

    public Sample2 getComp2() {
        return comp2;
    }
}
