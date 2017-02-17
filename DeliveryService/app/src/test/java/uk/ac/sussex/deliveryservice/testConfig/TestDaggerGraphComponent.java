package uk.ac.sussex.deliveryservice.testConfig;

import javax.inject.Singleton;

import dagger.Component;
import uk.ac.sussex.deliveryservice.DriverDetailsActivityTest;
import uk.ac.sussex.deliveryservice.config.DaggerGraphComponent;


@Singleton
@Component(modules = {TestMainModule.class})
public interface TestDaggerGraphComponent extends DaggerGraphComponent{

    void inject(DriverDetailsActivityTest driverDetailsActivityTest);

}
