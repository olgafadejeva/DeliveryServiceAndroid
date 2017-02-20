package uk.ac.sussex.deliveryservice.config;


import javax.inject.Singleton;

import dagger.Component;
import uk.ac.sussex.deliveryservice.DriverDetailsActivity;
import uk.ac.sussex.deliveryservice.LoginActivity;
import uk.ac.sussex.deliveryservice.VehiclesActivity;

@Singleton
@Component(modules = {MainModule.class})
public interface DaggerGraphComponent {
    void inject(LoginActivity loginActivity);

    void inject(DriverDetailsActivity driverDetailsActivity);

    void inject(VehiclesActivity vehiclesActivity);

    static final class Initializer {
        private Initializer() {
        }
        public static DaggerGraphComponent init(DaggerApplication app) {
            return DaggerDaggerGraphComponent.builder()
                    .mainModule(new MainModule(app))
                    .build();
        }
    }
}