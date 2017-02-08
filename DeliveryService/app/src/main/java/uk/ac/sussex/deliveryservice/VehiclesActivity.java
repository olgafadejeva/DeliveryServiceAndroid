package uk.ac.sussex.deliveryservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import uk.ac.sussex.deliveryservice.R;
import uk.ac.sussex.deliveryservice.adapters.CustomRouteInformationListAdapter;
import uk.ac.sussex.deliveryservice.adapters.VehiclesListAdapter;
import uk.ac.sussex.deliveryservice.model.Vehicle;

public class VehiclesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles);

        ArrayList<Vehicle> vehicles = new ArrayList<>();
        Vehicle c = new Vehicle("aa", "1234556");
        vehicles.add(c);
        VehiclesListAdapter adapter = new VehiclesListAdapter(vehicles, getApplicationContext());

        ListView listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

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
