package uk.ac.sussex.deliveryservice;

import android.content.Intent;
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
import uk.ac.sussex.deliveryservice.model.RouteViewModel;

public class RouteInformationActivity extends AppCompatActivity {

    private ArrayList<RouteViewModel> dataModels;
    private RouteViewModel dataModel;
    private ListView listView;
    private static CustomRouteInformationListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_information_content_main);

        Intent i = getIntent();
        dataModel = (RouteViewModel)i.getSerializableExtra("route");
        dataModels = new ArrayList<>();
        dataModels.add(dataModel);
        adapter= new CustomRouteInformationListAdapter(dataModels, getApplicationContext());

        listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    public void seeDeliveries(View v)
    {
        Intent intent = new Intent(RouteInformationActivity.this, RouteDeliveriesActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("deliveries", dataModel.getDeliveries());
        intent.putExtras(b);
        startActivity(intent);
    }

    public void mapView(View v)
    {
        Toast.makeText(this, "Clicked on Map view", Toast.LENGTH_LONG).show();
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


}