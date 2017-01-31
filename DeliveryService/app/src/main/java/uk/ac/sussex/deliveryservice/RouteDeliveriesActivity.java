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
        Intent i = getIntent();
        //dataModels = (ArrayList<Delivery>) i.getSerializableExtra("dataModels");

        String json = "[\n" +
                "  {\n" +
                "    \"StatusString\": \"New\",\n" +
                "    \"ID\": 5,\n" +
                "    \"ClientID\": 3,\n" +
                "    \"DeliveryStatusID\": 5,\n" +
                "    \"RouteID\": 5,\n" +
                "    \"ItemSize\": 0,\n" +
                "    \"DeliverBy\": \"2016-11-30T00:00:00\",\n" +
                "    \"ItemWeight\": 4,\n" +
                "    \"DeliveryStatus\": {\n" +
                "      \"ID\": 5,\n" +
                "      \"Status\": 0,\n" +
                "      \"ReasonFailed\": null,\n" +
                "      \"DeliveredDate\": null\n" +
                "    },\n" +
                "    \"Client\": {\n" +
                "      \"ID\": 3,\n" +
                "      \"FirstName\": \"Amex\",\n" +
                "      \"LastName\": \"Stadium\",\n" +
                "      \"Email\": \"amex@amex.com\",\n" +
                "      \"Address\": {\n" +
                "        \"ClientId\": 3,\n" +
                "        \"ID\": 7,\n" +
                "        \"LineOne\": \"Village Way\",\n" +
                "        \"LineTwo\": null,\n" +
                "        \"City\": \"Brighton\",\n" +
                "        \"PostCode\": \"BN1 9BL\",\n" +
                "        \"Lat\": 50.8607055,\n" +
                "        \"Lng\": -0.0812815\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"StatusString\": \"New\",\n" +
                "    \"ID\": 4,\n" +
                "    \"ClientID\": 3,\n" +
                "    \"DeliveryStatusID\": 5,\n" +
                "    \"RouteID\": 5,\n" +
                "    \"ItemSize\": 0,\n" +
                "    \"DeliverBy\": \"2016-11-30T00:00:00\",\n" +
                "    \"ItemWeight\": 4,\n" +
                "    \"DeliveryStatus\": {\n" +
                "      \"ID\": 5,\n" +
                "      \"Status\": 0,\n" +
                "      \"ReasonFailed\": null,\n" +
                "      \"DeliveredDate\": null\n" +
                "    },\n" +
                "    \"Client\": {\n" +
                "      \"ID\": 3,\n" +
                "      \"FirstName\": \"Amex\",\n" +
                "      \"LastName\": \"Stadium\",\n" +
                "      \"Email\": \"amex@amex.com\",\n" +
                "      \"Address\": {\n" +
                "        \"ClientId\": 3,\n" +
                "        \"ID\": 7,\n" +
                "        \"LineOne\": \"Village Way\",\n" +
                "        \"LineTwo\": null,\n" +
                "        \"City\": \"Brighton\",\n" +
                "        \"PostCode\": \"BN1 9BL\",\n" +
                "        \"Lat\": 50.8607055,\n" +
                "        \"Lng\": -0.0812815\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "]";

        Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        dataModels = gSon.fromJson(json,  new TypeToken<ArrayList<Delivery>>() {}.getType());
        //display dataModels in a list

        adapter= new CustomDeliveriesListAdapter(dataModels, getApplicationContext());

        listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Delivery dataModel= dataModels.get(position);

                Snackbar.make(view, "clicked on the delivery", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                Intent intent = new Intent(RouteDeliveriesActivity.this, RouteInformationActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("route", dataModel);
                intent.putExtras(b);
                //startActivity(intent);

            }
        });

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