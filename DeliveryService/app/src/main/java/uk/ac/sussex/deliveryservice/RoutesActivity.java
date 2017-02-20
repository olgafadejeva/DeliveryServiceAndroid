package uk.ac.sussex.deliveryservice;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import uk.ac.sussex.deliveryservice.adapters.CustomRouteListAdapter;
import uk.ac.sussex.deliveryservice.model.Delivery;
import uk.ac.sussex.deliveryservice.model.Route;
import uk.ac.sussex.deliveryservice.model.RouteViewModel;
import uk.ac.sussex.deliveryservice.tasks.GetRoutesTask;
import uk.ac.sussex.deliveryservice.util.ErrorAction;
import uk.ac.sussex.deliveryservice.util.RouteViewConverter;

public class RoutesActivity extends DeliveryServiceActivity {

    ArrayList<RouteViewModel> dataModels;
    ListView listView;
    private static CustomRouteListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes_content_main);

        listView=(ListView)findViewById(R.id.list);

        Bundle b = getIntent().getExtras();
        final String token = b.getString("token");

        dataModels = getRoutes(token);
        adapter= new CustomRouteListAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RouteViewModel dataModel= dataModels.get(position);

                Intent intent = new Intent(RoutesActivity.this, RouteInformationActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("route", dataModel);
                b.putString("token", token);
                intent.putExtras(b);
                startActivity(intent);

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_routes, menu);
        return true;
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

    public ArrayList<RouteViewModel> getRoutes(String token) {
        GetRoutesTask task = new GetRoutesTask();
        String[] params = new String[] {token};
        String json = null;
        ArrayList<RouteViewModel> models = new ArrayList<>();
        try {
            json = task.execute(params).get();
            if (json.equals("Error")) {
                ErrorAction.showErrorDialogAndFinishActivity(this);
            } else {
                models = RouteViewConverter.convertJsonToModels(json);
            }
        } catch (InterruptedException e) {
            ErrorAction.showErrorDialogAndFinishActivity(this);
        } catch (ExecutionException e) {
            ErrorAction.showErrorDialogAndFinishActivity(this);
        }

        return models;
        /*String json = "[\n" +
                "  {\n" +
                "    \"ID\":5,\n" +
                "    \"DeliverBy\":\"2016-11-19T00:00:00\",\n" +
                "    \"DeliveryDate\":\"2016-11-18T00:00:00\",\n" +
                "    \"OverallDistance\":888.65,\n" +
                "    \"OverallTimeRequired\":10.31,\n" +
                "    \"Deliveries\":[\n" +
                "      {\n" +
                "        \"StatusString\":\"New\",\n" +
                "        \"ID\":5,\n" +
                "        \"ClientID\":3,\n" +
                "        \"DeliveryStatusID\":5,\n" +
                "        \"RouteID\":5,\n" +
                "        \"ItemSize\":0,\n" +
                "        \"DeliverBy\":\"2016-11-30T00:00:00\",\n" +
                "        \"ItemWeight\":4.0,\n" +
                "        \"DeliveryStatus\":{\n" +
                "          \"ID\":5,\n" +
                "          \"Status\":0,\n" +
                "          \"ReasonFailed\":null,\n" +
                "          \"DeliveredDate\":null\n" +
                "        },\n" +
                "        \"Client\":{\n" +
                "          \"ID\":3,\n" +
                "          \"FirstName\":\"Amex\",\n" +
                "          \"LastName\":\"Stadium\",\n" +
                "          \"Email\":\"amex@amex.com\",\n" +
                "          \"Address\":{\n" +
                "            \"ClientId\":3,\n" +
                "            \"ID\":7,\n" +
                "            \"LineOne\":\"Village Way\",\n" +
                "            \"LineTwo\":null,\n" +
                "            \"City\":\"Brighton\",\n" +
                "            \"PostCode\":\"BN1 9BL\",\n" +
                "            \"Lat\":50.8607055,\n" +
                "            \"Lng\":-0.0812815\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"PickUpAddress\":{\n" +
                "      \"ID\":3,\n" +
                "      \"LineOne\":\"75 Newmarket Road\",\n" +
                "      \"LineTwo\":null,\n" +
                "      \"City\":\"Brighton\",\n" +
                "      \"PostCode\":\"BN2 3QF\",\n" +
                "      \"Lat\":50.8372843,\n" +
                "      \"Lng\":-0.121588\n" +
                "    },\n" +
                "    \"RouteStatusString\":\"Completed\",\n" +
                "    \"Vehicle\":{\n" +
                "      \"ID\":1,\n" +
                "      \"VehicleName\":\"Volkswagen\",\n" +
                "      \"RegistrationNumber\":\"12457856\",\n" +
                "      \"Height\":122.0,\n" +
                "      \"Width\":79.0,\n" +
                "      \"Length\":14.0,\n" +
                "      \"MaxLoad\":200.0\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"ID\":6,\n" +
                "    \"DeliverBy\":\"2016-11-29T00:00:00\",\n" +
                "    \"DeliveryDate\":\"2016-11-28T00:00:00\",\n" +
                "    \"OverallDistance\":881.36,\n" +
                "    \"OverallTimeRequired\":9.76,\n" +
                "    \"Deliveries\":[\n" +
                "      {\n" +
                "        \"StatusString\":\"New\",\n" +
                "        \"ID\":2,\n" +
                "        \"ClientID\":2,\n" +
                "        \"DeliveryStatusID\":2,\n" +
                "        \"RouteID\":6,\n" +
                "        \"ItemSize\":0,\n" +
                "        \"DeliverBy\":\"2016-11-20T00:00:00\",\n" +
                "        \"ItemWeight\":0.0,\n" +
                "        \"DeliveryStatus\":{\n" +
                "          \"ID\":2,\n" +
                "          \"Status\":0,\n" +
                "          \"ReasonFailed\":\"client not present\",\n" +
                "          \"DeliveredDate\":\"2016-12-02T15:31:53.3260657\"\n" +
                "        },\n" +
                "        \"Client\":{\n" +
                "          \"ID\":2,\n" +
                "          \"FirstName\":\"Brighon\",\n" +
                "          \"LastName\":\"Station\",\n" +
                "          \"Email\":\"station@gmai.com\",\n" +
                "          \"Address\":{\n" +
                "            \"ClientId\":2,\n" +
                "            \"ID\":4,\n" +
                "            \"LineOne\":\"Queens Rd\",\n" +
                "            \"LineTwo\":null,\n" +
                "            \"City\":\"Brighton\",\n" +
                "            \"PostCode\":\"BN1 3XP\",\n" +
                "            \"Lat\":50.829,\n" +
                "            \"Lng\":-0.14125\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"PickUpAddress\":{\n" +
                "      \"ID\":11,\n" +
                "      \"LineOne\":\"Bristol Airport\",\n" +
                "      \"LineTwo\":null,\n" +
                "      \"City\":\"Bristol\",\n" +
                "      \"PostCode\":\"BS48 3DY\",\n" +
                "      \"Lat\":51.3859,\n" +
                "      \"Lng\":-2.7135463\n" +
                "    },\n" +
                "    \"RouteStatusString\":\"Completed\",\n" +
                "    \"Vehicle\":{\n" +
                "      \"ID\":1,\n" +
                "      \"VehicleName\":\"Volkswagen\",\n" +
                "      \"RegistrationNumber\":\"12457856\",\n" +
                "      \"Height\":122.0,\n" +
                "      \"Width\":79.0,\n" +
                "      \"Length\":14.0,\n" +
                "      \"MaxLoad\":200.0\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"ID\":7,\n" +
                "    \"DeliverBy\":\"2016-11-30T00:00:00\",\n" +
                "    \"DeliveryDate\":\"2016-11-29T00:00:00\",\n" +
                "    \"OverallDistance\":21.63,\n" +
                "    \"OverallTimeRequired\":0.95,\n" +
                "    \"Deliveries\":[\n" +
                "      {\n" +
                "        \"StatusString\":\"Picked up\",\n" +
                "        \"ID\":3,\n" +
                "        \"ClientID\":1,\n" +
                "        \"DeliveryStatusID\":3,\n" +
                "        \"RouteID\":7,\n" +
                "        \"ItemSize\":0,\n" +
                "        \"DeliverBy\":\"2016-12-02T00:00:00\",\n" +
                "        \"ItemWeight\":0.0,\n" +
                "        \"DeliveryStatus\":{\n" +
                "          \"ID\":3,\n" +
                "          \"Status\":1,\n" +
                "          \"ReasonFailed\":null,\n" +
                "          \"DeliveredDate\":null\n" +
                "        },\n" +
                "        \"Client\":{\n" +
                "          \"ID\":1,\n" +
                "          \"FirstName\":\"Boots\",\n" +
                "          \"LastName\":\"Coppergate\",\n" +
                "          \"Email\":\"boots@coots.com\",\n" +
                "          \"Address\":{\n" +
                "            \"ClientId\":1,\n" +
                "            \"ID\":1,\n" +
                "            \"LineOne\":\"12 Coppergate Street\",\n" +
                "            \"LineTwo\":null,\n" +
                "            \"City\":\"York\",\n" +
                "            \"PostCode\":\"YO1 9NT\",\n" +
                "            \"Lat\":53.9576494,\n" +
                "            \"Lng\":-1.080128\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"PickUpAddress\":{\n" +
                "      \"ID\":3,\n" +
                "      \"LineOne\":\"75 Newmarket Road\",\n" +
                "      \"LineTwo\":null,\n" +
                "      \"City\":\"Brighton\",\n" +
                "      \"PostCode\":\"BN2 3QF\",\n" +
                "      \"Lat\":50.8372843,\n" +
                "      \"Lng\":-0.121588\n" +
                "    },\n" +
                "    \"RouteStatusString\":\"In progress\",\n" +
                "    \"Vehicle\":{\n" +
                "      \"ID\":1,\n" +
                "      \"VehicleName\":\"Volkswagen\",\n" +
                "      \"RegistrationNumber\":\"12457856\",\n" +
                "      \"Height\":122.0,\n" +
                "      \"Width\":79.0,\n" +
                "      \"Length\":14.0,\n" +
                "      \"MaxLoad\":200.0\n" +
                "    }\n" +
                "  }\n" +
                "]";


        Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        List<Route> routes = gSon.fromJson(json,  new TypeToken<ArrayList<Route>>() {}.getType());
        ArrayList<RouteViewModel> models = new ArrayList<>();
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        for (Route route : routes) {
            RouteViewModel model = new RouteViewModel();
            model.setDeliveryDate(formatter.format(route.getDeliveryDate()));
            model.setID(route.getID());
            model.setStatus(route.getRouteStatusString());
            model.setDeliveries((ArrayList< Delivery>) route.getDeliveries());
            model.setDeliverByDate(formatter.format(route.getDeliverBy()));
            model.setOverallDistance(route.getOverallDistance());
            model.setOverallTime(route.getOverallTimeRequired());
            model.setPickUpAddress(route.getPickUpAddress());
            model.setVehicle(route.getVehicle());
            models.add(model);
        }
        return models;
*/

        /*try {
            String result = task.execute(token).get();
            if (!result.equals("Fail")) {
                Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                List<Route> routes = gSon.fromJson(result,  new TypeToken<ArrayList<Route>>() {}.getType());
                System.out.println(routes.size());
                //display routes
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
    }
}
