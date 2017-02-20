package uk.ac.sussex.deliveryservice.testConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.ac.sussex.deliveryservice.tasks.AccessDriverDetailsTask;
import uk.ac.sussex.deliveryservice.tasks.GetVehiclesTask;
import uk.ac.sussex.deliveryservice.tasks.LoginTask;


@Module
public class TestMainModule  {
    TestDaggerApplication app;
    public TestMainModule(TestDaggerApplication application) {
        app = application;
    }

    AccessDriverDetailsTask accessDriverDetailsTask;
    GetVehiclesTask getVehiclesTask;


    @Provides
    @Singleton
    LoginTask provideLoginTask() {
        return new LoginTask();
    }

    @Provides
    @Singleton
    AccessDriverDetailsTask provideAccessDriverDetailsTask() {
        return accessDriverDetailsTask;
    }

    @Provides
    @Singleton
    GetVehiclesTask provideGetVehiclesTask() {
        return getVehiclesTask;
    }

    public void setAccessDriverDetailsTask(AccessDriverDetailsTask task) {
        this.accessDriverDetailsTask = task;
    }


    public void setGetVehiclesTask(GetVehiclesTask task) {
        this.getVehiclesTask = task;
    }
}


