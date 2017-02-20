package uk.ac.sussex.deliveryservice;


import android.widget.ListView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import uk.ac.sussex.deliveryservice.testConfig.TestDaggerApplication;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants =BuildConfig.class,  sdk = 22, application =TestDaggerApplication.class)
public class MainMenuActivityTest {

    @Test
    public void testActivityIsPopulatedCorrectlyWhenHolidaysArePresentInJson() {
        MainMenuActivity activity = (MainMenuActivity) Robolectric.buildActivity(MainMenuActivity.class).create().get();
        ListView listView =(ListView) activity.findViewById(R.id.list);
        assertEquals(3,listView.getCount());
    }

}
