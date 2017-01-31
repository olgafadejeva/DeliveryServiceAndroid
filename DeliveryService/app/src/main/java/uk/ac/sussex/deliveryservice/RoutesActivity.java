package uk.ac.sussex.deliveryservice;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import uk.ac.sussex.deliveryservice.adapters.CustomRouteListAdapter;
import uk.ac.sussex.deliveryservice.model.Delivery;
import uk.ac.sussex.deliveryservice.model.Route;
import uk.ac.sussex.deliveryservice.model.RouteViewModel;

public class RoutesActivity extends AppCompatActivity {

    ArrayList<RouteViewModel> dataModels;
    ListView listView;
    private static CustomRouteListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes_content_main);

        listView=(ListView)findViewById(R.id.list);



        //setContentView(R.layout.activity_routes);
        Bundle b = getIntent().getExtras();
        String key = "";
        if(b != null) {
            key = b.getString("key");
        }

        dataModels = getRoutes(key);
        adapter= new CustomRouteListAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RouteViewModel dataModel= dataModels.get(position);

                Snackbar.make(view, dataModel.getDeliveryDate()+"\n"+dataModel.getStatus()+" API: ", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                Intent intent = new Intent(RoutesActivity.this, RouteInformationActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("route", dataModel);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<RouteViewModel> getRoutes(String token) {
        //GetRoutesTask task = new GetRoutesTask();
        String json = "[\n" +
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
            models.add(model);
        }
        return models;


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

    private class GetRoutesTask extends AsyncTask<String, Void, String> {

        private static final String LOGIN_URL = "http://192.168.1.7:44302/androidActionsApi/routes";
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10000, TimeUnit.SECONDS)
                    .build();

            Request request =
                    new Request.Builder()
                            .url(LOGIN_URL)
                            .header("Authorization", "Bearer " + params[0])
                            .get()
                            .build();


            Response response = null;
            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return "Fail";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return "Fail";
        }


        @Override
        protected void onPostExecute(String result) {
            // textView.setText(result);
        }
    }





}
