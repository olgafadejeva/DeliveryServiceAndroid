package uk.ac.sussex.deliveryservice;


import android.support.v7.app.AlertDialog;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.File;
import java.io.IOException;

import uk.ac.sussex.deliveryservice.mocks.GetVehiclesTestTask;
import uk.ac.sussex.deliveryservice.model.Vehicle;
import uk.ac.sussex.deliveryservice.testConfig.TestDaggerApplication;
import uk.ac.sussex.deliveryservice.testConfig.TestMainModule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/*
Test class for a Vehicles activity. Tests whether the error dialog is shown when  a task fails
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants =BuildConfig.class,  sdk = 22, application =TestDaggerApplication.class)
public class VehiclesActivityTest {

    GetVehiclesTestTask task;

    @Before
    public void setup() {
        TestDaggerApplication app = (TestDaggerApplication) RuntimeEnvironment.application;

        TestMainModule testModule = new TestMainModule(app);
        task  = new GetVehiclesTestTask();

        testModule.setGetVehiclesTask(task);

        app.setModules(testModule);
    }


    @Test
    public void testActivityIsPopulatedCorrectlyWithVehicles() {
        String json = "";
        try {
            json = FileUtils.readFileToString(new File("D:\\DeliveryServiceAndroid\\DeliveryService\\app\\src\\test\\java\\uk\\ac\\sussex\\deliveryservice\\resources\\vehicles.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        task.setTestResult(json);
        VehiclesActivity activity = (VehiclesActivity) Robolectric.buildActivity(VehiclesActivity.class).create().get();
        ListView list = (ListView) activity.findViewById(R.id.list);
        assertEquals(2, list.getCount());

        Vehicle vehicleOne = (Vehicle) list.getAdapter().getItem(0);
        assertEquals("Audi", vehicleOne.getVehicleName());
        assertEquals("123456", vehicleOne.getRegistrationNumber());

        Vehicle vehicleTwo = (Vehicle) list.getAdapter().getItem(1);
        assertEquals("BMW", vehicleTwo.getVehicleName());
        assertEquals("7891011", vehicleTwo.getRegistrationNumber());

        //test to see that the dialog was shown to the user
        AlertDialog dialog = activity.getLastDialog();
        assertNull(dialog);
    }

    @Test
    public void testActivityWhenErrorIsReturned() {
        String errorResult = "Error";
        task.setTestResult(errorResult);

        VehiclesActivity activity = (VehiclesActivity) Robolectric.buildActivity(VehiclesActivity.class).create().get();
        ListView list = (ListView) activity.findViewById(R.id.list);
        assertEquals(0, list.getCount());

        //test to see that the alert dialog was shown to the user
        AlertDialog dialog = activity.getLastDialog();
        assertNotNull(dialog);

        TextView dialogMessage = (TextView) dialog.findViewById(android.R.id.message);
        assertEquals("Unknown error, please try again later ", dialogMessage.getText());
    }
}
