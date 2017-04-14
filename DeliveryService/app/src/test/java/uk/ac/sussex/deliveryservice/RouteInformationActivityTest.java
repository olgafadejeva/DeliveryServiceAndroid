package uk.ac.sussex.deliveryservice;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import uk.ac.sussex.deliveryservice.model.RouteViewModel;
import uk.ac.sussex.deliveryservice.testConfig.TestDaggerApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/*
Test class for a RouteInformationAcrivity . Tests whether the error dialog when there is no token present in the request
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants =BuildConfig.class,  sdk = 22, application =TestDaggerApplication.class)
public class RouteInformationActivityTest {

    @Test
    public void testActivityIsPopulatedCorrectlyWhenAllDataIsPresentInIntent() {
        Intent intent = new Intent();

        Bundle bundle = new Bundle();
        RouteViewModel model = new RouteViewModel();
        bundle.putSerializable("route", model);
        intent.putExtras(bundle);

        String token = "tokenString12345";
        intent.putExtra("token", token);
        RouteInformationActivity activity = Robolectric.buildActivity(RouteInformationActivity.class)
                .withIntent(intent)
                .create()
                .get();

        ListView list = (ListView) activity.findViewById(R.id.list);
        assertEquals(1, list.getCount());
        assertEquals(model, list.getAdapter().getItem(0));


        AlertDialog dialog = activity.getLastDialog();
        assertNull(dialog);
    }

    @Test
    public void testActivityErrorsWhenDataModelIsNotPresentInIntent() {
        Intent intent = new Intent();
        RouteInformationActivity activity =  Robolectric.buildActivity(RouteInformationActivity.class)
                .withIntent(intent)
                .create()
                .get();

        AlertDialog dialog = activity.getLastDialog();
        assertNotNull(dialog);

        TextView dialogMessage = (TextView) dialog.findViewById(android.R.id.message);
        assertEquals("Unknown error, please try again later ", dialogMessage.getText());
    }

    @Test
    public void testActivityErrorsWhenTokenIsNotPresentInIntent() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("route", new RouteViewModel());
        intent.putExtras(bundle);
        RouteInformationActivity activity =  Robolectric.buildActivity(RouteInformationActivity.class)
                .withIntent(intent)
                .create()
                .get();

        AlertDialog dialog = activity.getLastDialog();
        assertNotNull(dialog);

        TextView dialogMessage = (TextView) dialog.findViewById(android.R.id.message);
        assertEquals("Unknown error, please try again later ", dialogMessage.getText());
    }
}

