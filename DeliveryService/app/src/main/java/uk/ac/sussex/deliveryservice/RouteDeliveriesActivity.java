package uk.ac.sussex.deliveryservice;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import uk.ac.sussex.deliveryservice.adapters.CustomDeliveriesListAdapter;
import uk.ac.sussex.deliveryservice.model.Delivery;
import uk.ac.sussex.deliveryservice.model.RouteViewModel;

public class RouteDeliveriesActivity extends AppCompatActivity {

    private ArrayList<Delivery> dataModels;
    ListView listView;
    private static CustomDeliveriesListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliveries_content_main);
        Intent intent = getIntent();
        Bundle bundle =intent.getExtras();
        String token = "";
        if(bundle != null) {
            token = bundle.getString("token");
        }
        dataModels = (ArrayList<Delivery>) intent.getSerializableExtra("deliveries");

        //display dataModels in a list
        adapter= new CustomDeliveriesListAdapter(dataModels, this, token);
        //adapter.setToken(token);
        adapter.setActivity(this);

        listView=(ListView)findViewById(R.id.list);
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
