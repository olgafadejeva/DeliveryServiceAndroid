package uk.ac.sussex.deliveryservice;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import uk.ac.sussex.deliveryservice.model.Address;
import uk.ac.sussex.deliveryservice.model.Delivery;
import uk.ac.sussex.deliveryservice.model.RouteViewModel;

public class NavigationActivity extends AppCompatActivity {

    private RouteViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Intent i = getIntent();
        model = (RouteViewModel)i.getSerializableExtra("route");
        createButtons();
    }

    private void createButtons() {
        ViewGroup linearLayout = (ViewGroup) findViewById(R.id.activity_navigation);
        final ArrayList<Delivery> deliveries = model.getDeliveries();
        for (int i =0; i< deliveries.size(); i++) {
            final Delivery delivery = deliveries.get(i);
            Button navigationButton =  new Button(this);
            String clientPostCode = delivery.getClient().getAddress().getPostCode();
            navigationButton.setText("Navigate to client #" + i + "\n" + clientPostCode);

            navigationButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT ,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            navigationButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Address clientAddress = delivery.getClient().getAddress();
                    String destinationUri = "google.navigation:q=" + clientAddress.getLat() + "," + clientAddress.getLng();
                    Uri gmmIntentUri = Uri.parse(destinationUri);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            });

            linearLayout.addView(navigationButton);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void navigateToDepot(View view) {
        Address depotAddress = model.getPickUpAddress();
        String destinationUri = "google.navigation:q=" + depotAddress.getLat() + "," + depotAddress.getLng();
        Uri gmmIntentUri = Uri.parse(destinationUri);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
