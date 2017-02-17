package uk.ac.sussex.deliveryservice.testConfig;

import uk.ac.sussex.deliveryservice.config.DaggerApplication;
import uk.ac.sussex.deliveryservice.config.DaggerGraphComponent;

public class TestDaggerApplication extends DaggerApplication {

    private static TestDaggerGraphComponent testApplicationComponent;
    private static TestDaggerApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        buildComponentGraph();
    }

    public static DaggerGraphComponent component() {
        return testApplicationComponent;
    }
    public static void buildComponentGraph() {
        //testApplicationComponent = TestDaggerGraphComponent.Initializer.init(instance);
    }

    @Override
    public TestDaggerGraphComponent getOrCreateApplicationComponent() {
        if (testApplicationComponent == null) {
            return DaggerTestDaggerGraphComponent.builder().testMainModule(new TestMainModule(this))
                    .build();
        }
        return testApplicationComponent;
    }


  /*  public TestDaggerGraphComponent getOrCreateApplicationTestComponent() {
        if (testApplicationComponent == null) {
            return DaggerTestDaggerGraphComponent.builder().testMainModule(new TestMainModule(this))
                    .build();
        }
        return testApplicationComponent;
    }*/


}