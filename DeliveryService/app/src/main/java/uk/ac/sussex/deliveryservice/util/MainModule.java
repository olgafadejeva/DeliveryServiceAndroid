package uk.ac.sussex.deliveryservice.util;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.ac.sussex.deliveryservice.tasks.LoginTask;

@Module
public class MainModule {
    DaggerApplication app;
    public MainModule(DaggerApplication application) {
        app = application;
    }
    @Provides
    @Singleton
    protected Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    LoginTask provideLoginTask() {
        return new LoginTask();
    }
}