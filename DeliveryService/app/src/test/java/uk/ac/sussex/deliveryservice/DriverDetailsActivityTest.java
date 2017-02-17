package uk.ac.sussex.deliveryservice;

import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import uk.ac.sussex.deliveryservice.tasks.AccessDriverDetailsTask;
import uk.ac.sussex.deliveryservice.testConfig.TestDaggerApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(RobolectricTestRunner.class)
@Config(constants =BuildConfig.class,  sdk = 22, application =TestDaggerApplication.class)
public class DriverDetailsActivityTest {

    @Inject
    AccessDriverDetailsTask task;

    @Before
    public void setup() {
        ((TestDaggerApplication)RuntimeEnvironment.application).getOrCreateApplicationComponent().inject(this);
    }


    @Test
    public void testActivityIsPopulatedCorrectlyWhenHolidaysArePresentInJson() {
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
    }
}
