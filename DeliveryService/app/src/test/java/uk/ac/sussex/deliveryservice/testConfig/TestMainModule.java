package uk.ac.sussex.deliveryservice.testConfig;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.ac.sussex.deliveryservice.tasks.AccessDriverDetailsTask;
import uk.ac.sussex.deliveryservice.tasks.LoginTask;


@Module
public class TestMainModule  {
    TestDaggerApplication app;
    public TestMainModule(TestDaggerApplication application) {
        app = application;
    }


    @Provides
    @Singleton
    LoginTask provideLoginTask() {
        return new LoginTask();
    }

    @Provides
    @Singleton
    AccessDriverDetailsTask provideAccessDriverDetailsTask() {
        return new AccessDriverDetailsSuccessTask();
    }

}


class AccessDriverDetailsSuccessTask extends AccessDriverDetailsTask{
    @Override
    protected String doInBackground(String... params) {
        try {
            String json = FileUtils.readFileToString(new File("D:\\DeliveryServiceAndroid\\DeliveryService\\app\\src\\test\\java\\uk\\ac\\sussex\\deliveryservice\\resources\\driverDetails.json"));
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }
}