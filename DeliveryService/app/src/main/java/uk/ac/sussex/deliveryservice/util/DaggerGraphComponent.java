package uk.ac.sussex.deliveryservice.util;


import javax.inject.Singleton;

import dagger.Component;
import uk.ac.sussex.deliveryservice.LoginActivity;

@Singleton
@Component(modules = {MainModule.class})
public interface DaggerGraphComponent {
    void inject(LoginActivity loginActivity);
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