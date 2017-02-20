package uk.ac.sussex.deliveryservice;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TableLayout;
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

import uk.ac.sussex.deliveryservice.mocks.AccessDriverDetailsTestsTask;
import uk.ac.sussex.deliveryservice.testConfig.TestDaggerApplication;
import uk.ac.sussex.deliveryservice.testConfig.TestMainModule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@RunWith(RobolectricTestRunner.class)
@Config(constants =BuildConfig.class,  sdk = 22, application =TestDaggerApplication.class)
public class DriverDetailsActivityTest {

    AccessDriverDetailsTestsTask task;
    @Before
    public void setup() {
        TestDaggerApplication app = (TestDaggerApplication) RuntimeEnvironment.application;

        TestMainModule testModule = new TestMainModule(app);
        task  = new AccessDriverDetailsTestsTask();

        testModule.setAccessDriverDetailsTask(task);

        app.setModules(testModule);
    }


    @Test
    public void testActivityIsPopulatedCorrectlyWhenHolidaysArePresentInJson() {
        String json = "";
        try {
            json = FileUtils.readFileToString(new File("D:\\DeliveryServiceAndroid\\DeliveryService\\app\\src\\test\\java\\uk\\ac\\sussex\\deliveryservice\\resources\\driverDetails.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        task.setTestResult(json);
        DriverDetailsActivity activity = (DriverDetailsActivity) Robolectric.buildActivity(DriverDetailsActivity.class).create().get();
        TextView emailView = (TextView) activity.findViewById(R.id.email);
        String email = emailView.getText().toString();
        assertEquals("rebecca@gmail.com", email);

        TextView driverAddress = (TextView) activity.findViewById(R.id.address);
        String address = driverAddress.getText().toString();
        assertTrue(address.contains("25 Victor Street"));
        assertTrue(address.contains("York"));
        assertTrue(address.contains("YO1 6HQ"));

        TableLayout tl = (TableLayout) activity.findViewById(R.id.holidays_table);
        assertEquals(View.VISIBLE, tl.getVisibility()); //check layout is visible as there are holidays
        assertEquals(2, tl.getChildCount()); //1 header and 1 actual holiday

        //test to see that the dialog was shown to the user
        AlertDialog dialog = activity.getLastDialog();
        assertNull(dialog);
    }

    @Test
    public void testActivityWhenErrorIsReturned() {
        String errorResult = "Error";
        task.setTestResult(errorResult);

        DriverDetailsActivity activity = (DriverDetailsActivity) Robolectric.buildActivity(DriverDetailsActivity.class).create().get();
        TextView emailView = (TextView) activity.findViewById(R.id.email);
        String email = emailView.getText().toString();
        assertEquals("", email);

        TextView driverAddress = (TextView) activity.findViewById(R.id.address);
        String address = driverAddress.getText().toString();
        assertEquals("", address);


        TableLayout tl = (TableLayout) activity.findViewById(R.id.holidays_table);
        assertEquals(View.INVISIBLE, tl.getVisibility()); //check layout is invisible  as there are no holidays

        //test to see that the alert dialog was shown to the user
        AlertDialog dialog = activity.getLastDialog();
        assertNotNull(dialog);

        TextView dialogMessage = (TextView) dialog.findViewById(android.R.id.message);
        assertEquals("Unknown error, please try again later ", dialogMessage.getText());
    }
}
