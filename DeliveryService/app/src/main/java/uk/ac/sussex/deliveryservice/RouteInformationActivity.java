package uk.ac.sussex.deliveryservice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import uk.ac.sussex.deliveryservice.adapters.CustomDeliveriesListAdapter;
import uk.ac.sussex.deliveryservice.adapters.CustomRouteInformationListAdapter;
import uk.ac.sussex.deliveryservice.model.Address;
import uk.ac.sussex.deliveryservice.model.RouteViewModel;
import uk.ac.sussex.deliveryservice.util.ErrorAction;

/*
Shows detailed information about the selected route
 */
public class RouteInformationActivity extends DeliveryServiceActivity {

    private ArrayList<RouteViewModel> dataModels;
    private RouteViewModel dataModel;
    private ListView listView;
    String token;
    private static CustomRouteInformationListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_information_content_main);

        Intent i = getIntent();
        dataModel = (RouteViewModel)i.getSerializableExtra("route");
        if (dataModel==null) {
            ErrorAction.showErrorDialogAndFinishActivity(this);
        } else {
            dataModels = new ArrayList<>();
            dataModels.add(dataModel);
            token = i.getStringExtra("token");
            if (token == null) {
                ErrorAction.showErrorDialogAndFinishActivity(this);
            } else {
                adapter = new CustomRouteInformationListAdapter(dataModels, getApplicationContext());

                listView = (ListView) findViewById(R.id.list);
                listView.setAdapter(adapter);
            }
        }
    }

    public void seeDeliveries(View v)
    {
        Intent intent = new Intent(RouteInformationActivity.this, RouteDeliveriesActivity.class);
        Bundle b = new Bundle();
        b.putString("token", token);
        b.putSerializable("deliveries", dataModel.getDeliveries());
        intent.putExtras(b);
        startActivity(intent);
    }

    public void mapView(View v)
    {
        Intent intent = new Intent(RouteInformationActivity.this, MapRouteBoundaryActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("route", dataModel);
        intent.putExtras(b);
        startActivity(intent);
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


    public void startNavigation(View view) {
        Intent intent = new Intent(RouteInformationActivity.this, NavigationActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("route", dataModel);
        intent.putExtras(b);
        startActivity(intent);
    }
}
