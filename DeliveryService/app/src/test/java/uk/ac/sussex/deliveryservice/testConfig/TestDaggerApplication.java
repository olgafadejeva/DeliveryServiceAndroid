package uk.ac.sussex.deliveryservice.testConfig;

import uk.ac.sussex.deliveryservice.config.DaggerApplication;
import uk.ac.sussex.deliveryservice.config.DaggerGraphComponent;

public class TestDaggerApplication extends DaggerApplication {

    private static TestDaggerGraphComponent testApplicationComponent;
    private static TestDaggerApplication instance;
    private static TestMainModule applicationModule;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        buildComponentGraph();
    }

    public void setModules(TestMainModule applicationModule) {
        this.applicationModule = applicationModule;
    }


    @Override
    public TestDaggerGraphComponent getOrCreateApplicationComponent() {
        if (testApplicationComponent == null) {
            if (applicationModule == null)
            return DaggerTestDaggerGraphComponent.builder().testMainModule(new TestMainModule(this))
                    .build();
            else {
                return DaggerTestDaggerGraphComponent.builder().testMainModule(applicationModule)
                        .build();
            }
        }
        return testApplicationComponent;
    }
}