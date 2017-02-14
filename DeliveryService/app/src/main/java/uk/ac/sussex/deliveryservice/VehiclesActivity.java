package uk.ac.sussex.deliveryservice;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import uk.ac.sussex.deliveryservice.adapters.VehiclesListAdapter;
import uk.ac.sussex.deliveryservice.model.Vehicle;
import uk.ac.sussex.deliveryservice.tasks.GetVehiclesTask;
import uk.ac.sussex.deliveryservice.util.ErrorAction;

public class VehiclesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles);

        String vehiclesJson = null;
        GetVehiclesTask task = new GetVehiclesTask();
        try {
            vehiclesJson = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (vehiclesJson.equals("Error")) {
            ErrorAction.showErrorDialogAndFinishActivity(this);
        } else {

            Gson gSon = new GsonBuilder().create();
            ArrayList<Vehicle> vehicles = gSon.fromJson(vehiclesJson, new TypeToken<ArrayList<Vehicle>>() {
            }.getType());
            //ArrayList<Vehicle> vehicles = new ArrayList<>();
        /*Vehicle c = new Vehicle("aa", "1234556");
        vehicles.add(c);*/
            VehiclesListAdapter adapter = new VehiclesListAdapter(vehicles, getApplicationContext());

            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);
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
}
